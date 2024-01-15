import {Card, Form, Input, Button, message, DatePicker} from "antd";
import logo from "../../assets/logoNoBg.png";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import {fetchLogin} from "../../store/modules/user";
import {signUpAPI} from "../../apis/user";

const Signup = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const onFinish = async (formValue) => {
        console.log(formValue)
        const response = await signUpAPI(formValue);
        console.log(response);
        navigate("/");
        message.success("User registration in process");
    };

    return (
        <div className="h-full w-full absolute left-0 top-0 bg-slate-400">
            <Card className="w-96 h-100 absolute left-1/2 top-1/2 transform -translate-x-1/2 -translate-y-1/2 bg-slate-700">
                <img
                    className="block mt-0 ml-auto mr-auto mb-5"
                    width={170}
                    height={50}
                    src={logo}
                    alt="LOGO"
                />

                <Form validateTrigger="onBlur" onFinish={onFinish}>
                    <Form.Item
                        name="username"
                        rules={[
                            {
                                required: true,
                                message: "Enter your username",
                            },
                        ]}
                    >
                        <Input size="large" placeholder="Enter your username" />
                    </Form.Item>

                    <Form.Item
                        name="password"
                        rules={[
                            {
                                required: true,
                                message: "Enter your password",
                            },
                        ]}
                    >
                        <Input
                            size="large"
                            placeholder="enter the password"
                            rules={[
                                {
                                    required: true,
                                    message: "Enter your username",
                                },
                            ]}
                        />
                    </Form.Item>
                    <Form.Item
                        name="fullName"
                        rules={[
                            {
                                required: true,
                                message: "Enter your full name",
                            },
                        ]}
                    >
                        <Input
                            size="large"
                            placeholder="enter your full name"
                            rules={[
                                {
                                    required: true,
                                    message: "Enter your username",
                                },
                            ]}
                        />
                    </Form.Item>
                    <Form.Item
                        name="email"
                        rules={[
                            {
                                required: true,
                                message: "Enter your email",
                            },
                        ]}

                    >
                        <Input
                            size="large"
                            placeholder="enter your email"
                            rules={[
                                {
                                    required: true,
                                    message: "Enter your email",
                                },
                            ]}
                        />
                    </Form.Item>

                    <Form.Item>
                        <Button type="primary" htmlType="submit" size="large" block>
                            Sign up
                        </Button>
                        <Button type="link" size="medium" block onClick={()=>navigate("/login")}>
                            Already have an account? Login
                        </Button>
                    </Form.Item>
                </Form>
            </Card>
        </div>
    );
};

export default Signup;
