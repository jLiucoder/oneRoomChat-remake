import {useNavigate} from "react-router-dom";
import {useCallback, useEffect, useRef, useState} from "react";
import {getChatsAPI, getTopUsersAPI} from "../../apis/user";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import ChatBubble from "../../components/ChatBubble";
import {Button, Form, Input, List, Modal, Popover} from "antd";
import {SendOutlined} from "@ant-design/icons";
import {useSelector} from "react-redux";
import SearchBox from "../../components/SearchBox";
import userInfoProcess from "../../util/userInfoProcess";

const Chat = () => {

    const currentUser = useSelector((state) => state.user);
    const [client, setClient] = useState(null);
    const [topUsers, setTopUsers] = useState([]);
    const navigate = useNavigate();
    const [messageList, setMessageList] = useState([]);
    const [form] = Form.useForm();
    const socket = new SockJS("http://localhost:8080/stomp");
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [searchTextRes, setSearchTextRes] = useState([])

    function handleSeach() {
        setIsModalOpen(true)
    }

    function handleCancel() {
        setIsModalOpen(false)
    }

    const getTopUser = useCallback(async () => {
        const users = await getTopUsersAPI();
        setTopUsers(userInfoProcess(users));
    }, []);

    function onContent() {

        return (
            <List
                itemLayout="horizontal"
                dataSource={topUsers}
                renderItem={(item, index) => (
                    <List.Item>
                        <List.Item.Meta
                            title={<a>{item}</a>}
                        />
                    </List.Item>
                )}
            />

        )
    }
    const onFinish = (values) => {

        let newMessage = JSON.stringify({
            messageText: values.message,
            msgDate: new Date(),
            userId: localStorage.getItem("userId"),
            username: currentUser.userInfo.username,
        })

        client.send("/app/chat", {}, newMessage);
        form.resetFields();

        setTimeout(() => {
            inputRef.current.focus();
        }, 0);
    }

    const inputRef = useRef(null);
    const searchRef = useRef(null);
    const scrollToBottom = () => {
        const scrollContainer = document.getElementById('message-container');
        scrollContainer.scrollTop = scrollContainer.scrollHeight;
    };

    const handleSearchResults = (results) => {
        setSearchTextRes(results)

    }

    useEffect(() => {
        const start = async () => {
            const chatHistory = await getChatsAPI();
            setMessageList(chatHistory);

        };
        start();
    }, []);

    useEffect(() => {
        const startFunction = async () => {
            const newClient = Stomp.over(socket);
            if (client != null) {
                client.disconnect();
            }
            newClient.connect({}, async () => {
                newClient.subscribe("/topic/messages", (payload) => {
                    // console.log(payload);
                    setMessageList((prev) => [...prev, JSON.parse(payload.body)]);
                });
            });

            setClient(newClient);
        };

        scrollToBottom();
        if (!Array.isArray(messageList) || messageList.length !== 0) {
            return;
        } else {
            if (Object.is(client, null)) {
                startFunction();
            }
        }
    }, [messageList]);

    const [focusInput, setFocusInput] = useState(() => () => {});
    const afterOpen = () => {
        focusInput();
    };

    return (
        <div className="flex flex-col h-full">
            <div className="flex w-full items-center pt-6 pl-4">
                <Popover content={onContent} title="Top Active User" trigger="hover" onOpenChange={(visible) => {
                    if (visible) {
                        getTopUser();
                    }
                }}>
                <h1 className="">Chat</h1>
            </Popover>
                <div className="flex justify-center w-4/5">

                    <Button className="bg-slate-500 w-90" onClick={handleSeach}>Seach chat</Button>
                    <Modal open={isModalOpen} onOk={handleCancel} onCancel={handleCancel}
                           okButtonProps={{style: {display: 'none'}}} cancelButtonProps={{style: {display: 'none'}}}
                           closable={false}
                           afterOpenChange={open => open && afterOpen()}

                    >
                        <SearchBox onSearch={handleSearchResults} setFocusInputMethod={setFocusInput}/>
                        <div className="flex flex-col">
                            {searchTextRes.map((searchText) => (
                                <div className="flex flex-row">
                                    <ChatBubble key={searchText.msgId} message={searchText.messageText}
                                                user={searchText.username}
                                                ifMine={searchText.userId == localStorage.getItem("userId")}
                                                chatBubble={false}/>
                                </div>
                            ))}
                        </div>
                    </Modal>
                </div>
            </div>

            <div id="message-container" className=" flex-grow overflow-y-auto pb-20">

                {messageList.map((message) => (
                    <ChatBubble key={message.msgId} message={message.messageText} user={message.username}
                                ifMine={message.userId == localStorage.getItem("userId")}
                                chatBubble={true}/>
                ))}

            </div>

            <div className="flex fixed bottom-5 justify-center left-0 right-0 px-4 pb-4">
                <Form className="w-3/5 h-6 flex" form={form}
                      onFinish={onFinish}>
                    <Form.Item
                        className="w-full h-10"
                        name="message"
                        tooltip={{
                            title: 'message',
                        }}
                    >
                        <Input ref={inputRef} className="h-12" placeholder="be nice!" autoComplete="off"/>
                    </Form.Item>
                    <Form.Item>
                        <Button className="h-12" type="primary" htmlType="submit"><SendOutlined/></Button>
                    </Form.Item>
                </Form>
            </div>

        </div>


    );
};

export default Chat;
