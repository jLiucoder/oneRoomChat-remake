import {SearchOutlined} from "@ant-design/icons";
import {Button, Input, Result} from "antd";
import {createRef, useEffect, useRef, useState} from "react";
import {searchChatAPI} from "../apis/user";

const SearchBox= ({onSearch, setFocusInputMethod})=>{
    const searchRef = useRef(null);

    useEffect(() => {
        // Expose focus method to parent
        setFocusInputMethod(() => () => searchRef.current?.focus());
    }, [setFocusInputMethod]);
    const onChange = async (e) => {
        const value = e.target.value;
        if(value === ""){
            onSearch([])
            return;
        }
        const response = await searchChatAPI(value);
        // change response to all lowercase

        if(response === null){
            onSearch([])
            return;
        }
        onSearch(response);
    }


    return (
        <div className="h-full w-full">
            <Input className="h-full w-full" type="text" placeholder="Search chat" onChange={onChange} ref={searchRef}/>
        </div>
    )
}
export default SearchBox;