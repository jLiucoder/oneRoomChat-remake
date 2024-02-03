import {useDispatch, useSelector} from "react-redux";
import {Button, DatePicker, Form, Input, message} from "antd";
import {useEffect} from "react";
import Avater from "../../components/Avater";
import moment from "moment";
import {updateProfileAPI} from "../../apis/user";
import {updateProfile} from "../../store/modules/user";

const Profile = () => {
    const user = useSelector((state) => state.user);
    const dispatch = useDispatch();
    const fullname = user.userInfo.fullName;
    const email = user.userInfo.email;
    const dob = user.userInfo.dob;
    const [userForm] = Form.useForm();

    useEffect(() => {
        console.log(user.userInfo)
        userForm.setFieldsValue({
            fullName: fullname,
            email: email,
            dob: dob ? moment(dob,"YYYY-MM-DD") : null,
        });
    }, [fullname, email, dispatch]);

    const onFinish = async (values) => {
        const newUser = {
            fullName: values.fullName,
            email: values.email,
            username: user.userInfo.username,
            userId: user.userInfo.userId,
        }
        message.success("Profile updated successfully, refresh to see changes");
        await dispatch(updateProfile(newUser));
    }
    return (
        <div className="">
            <h1 className="pl-6 pt-6">{user.userInfo.username} Profile</h1>
            <div className="flex justify-center items-start gap-4">

            <Form className="w-1/4 pt-2 pr-20" form={userForm} validateTrigger="onBlur" onFinish={onFinish} >
                <h3 className="pl-6 pt-1 text-slate-300">Full Name</h3>
                <Form.Item
                    name="fullName"

                    rules={
                    [
                        {
                            required: true,
                            message: "Modify your full name",
                        },
                    ]}
                >
                    <Input size="large"  placeholder={user.userInfo.fullName}/>
                </Form.Item>
                <h3 className="pl-6 pt-1 text-slate-300">Email</h3>
                <Form.Item
                    name="email"
                    rules={
                        [
                            {
                                required: true,
                                message: "Modify your email",
                            },
                        ]}
                >
                    <Input size="large"  placeholder={user.userInfo.email}/>
                </Form.Item>
                {/*<h3 className="pl-6 pt-1 text-slate-300">DOB</h3>*/}
                {/*<Form.Item*/}
                {/*    name="dob"*/}
                {/*    rules={*/}
                {/*    [*/}
                {/*        {*/}
                {/*            required: true,*/}
                {/*            message: "Modify your DOB",*/}
                {/*        },*/}
                {/*    ]}*/}
                {/*>*/}
                {/*    <DatePicker format="YYYY-MM-DD" />*/}
                {/*</Form.Item>*/}


                <Form.Item>
                    <Button className="w-3/5" type="primary" htmlType="submit" size="large" block>
                        Apply Changes
                    </Button>
                </Form.Item>
            </Form>
            </div>
        </div>
    );
};

export default Profile;
