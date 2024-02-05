import React, {useEffect, useState} from 'react';
import { LoadingOutlined, PlusOutlined } from '@ant-design/icons';
import { message, Upload } from 'antd';
import axios from "axios";
import {getPreSignedUrlAPI, saveProfilePictureAPI} from "../apis/user";
import {fetchUserInfo} from "../store/modules/user";
import {useDispatch, useSelector} from "react-redux";
const getBase64 = (img, callback) => {
    const reader = new FileReader();
    reader.addEventListener('load', () => callback(reader.result));
    reader.readAsDataURL(img);
};

const beforeUpload = (file) => {
    const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
    const isLt2M = file.size / 1024 / 1024 < 2;
    if (!isJpgOrPng) {
        message.error('You can only upload JPG/PNG file!');
    }else
    if (!isLt2M) {
        message.error('Image must smaller than 2MB!');
    }
    return isJpgOrPng && isLt2M;
};

const uploadToS3 = async (file, presignedUrl) => {
    // Directly upload the file to S3 with the pre-signed URL
    const result = await axios.put(presignedUrl, file, {
        headers: {
            'Content-Type': file.type,
        },
    });
    return result.config.url.split('?')[0]; // Return the S3 file URL without query parameters
};

const ProfilePicture = () => {
    const [loading, setLoading] = useState(false);
    const [imageUrl, setImageUrl] = useState();
    const dispatch = useDispatch();
    const user = useSelector((state) => state.user.userInfo);
    const handleChange = (info) => {
        if (info.file.status === 'uploading') {
            setLoading(true);
            return;
        }
        if (info.file.status === 'done') {
            // Get this url from response in real world.
            getBase64(info.file.originFileObj, (url) => {
                setLoading(false);
                setImageUrl(url);
            });
        }
    };

    useEffect(()=>{
        dispatch(fetchUserInfo());
        const url = user.profilePicture? user.profilePicture : null;
        setImageUrl(url);
    },[dispatch, user.profilePicture])

    const customRequest = async ({ file, onSuccess, onError }) => {
        try {
            // Request a pre-signed URL from your backend
            const response = await getPreSignedUrlAPI(file);
            // console.log(response)
            // const response = await axios.get(`/storage/generate-presigned-url?filename=${file.name}&contentType=${file.type}`);
            const presignedUrl = response;

            // Upload the file to S3
            const fileUrl = await uploadToS3(file, presignedUrl);

            const data = {
                url: fileUrl,
                userId: localStorage.getItem("userId")
            }

            // notify the server that the profile picture has been updated and save the URL to the user
            const response2 = await saveProfilePictureAPI(data);
            // fetch the updated user info with the profile picture

            message.success('profile picture upload successfully.');
            onSuccess(null, file);
        } catch (error) {
            console.error("Upload error:", error);
            onError(error);
        }
    };

    const uploadButton = (
        <button
            style={{
                border: 0,
                background: 'none',
            }}
            type="button"
        >
            {loading ? <LoadingOutlined /> : <PlusOutlined />}
            <div
                style={{
                    marginTop: 8,
                }}
            >
                Upload
            </div>
        </button>
    );
    return (
        <>
            <Upload
                name="avatar"
                listType="picture-card"
                className="avatar-uploader"
                showUploadList={false}
                // action="https://run.mocky.io/v3/435e224c-44fb-4773-9faf-380c5e6a2188"
                beforeUpload={beforeUpload}
                onChange={handleChange}
                customRequest={customRequest}
            >
                {imageUrl ? (
                    <img
                        src={imageUrl}
                        alt="avatar"
                        style={{
                            width: '100%',
                        }}
                    />
                ) : (
                    uploadButton
                )}
            </Upload>
        </>
    );
};
export default ProfilePicture;