import {Avatar} from "antd";

const ChatBubble = (props) => {

    // console.log("message is " + props.message + " user is " + props.user)
    // console.log(props.ifMine)
    let mine = props.ifMine;
    let p = props.chatBubble;
    return (

            mine && p ?
                <div className="pr-5 pt-2 flex items-center justify-end">
                    <div className="flex flex-col items-end">
                        <p className="text-sm">me</p>
                        <div className="pl-3 pr-3 bg-slate-400 text-lg rounded-lg">
                            <p>{props.message}</p>
                        </div>

                    </div>

                    <div className="ml-3">
                        <Avatar size="large">{props.user}</Avatar>
                    </div>
                </div>
                :
                <div className="pl-5 pt-2 flex items-center">
                    <div className="pr-3">
                        <Avatar size="large">{props.user}</Avatar>
                    </div>

                    <div className="flex flex-col items-start">
                        <p className="text-sm">{props.user}</p>
                        <div className="pl-3 pr-3 bg-slate-400 text-lg rounded-md">
                            <p>{props.message}</p>
                        </div>

                    </div>
                </div>



    );
}

export default ChatBubble;