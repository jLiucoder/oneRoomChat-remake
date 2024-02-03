import { Outlet, useLocation, useNavigate } from "react-router-dom";
import {Layout, List, Menu, Popconfirm, Popover} from "antd";
import {
  EditOutlined,
  FormOutlined,
  LogoutOutlined,
} from "@ant-design/icons";
import logo from "../../assets/logoNoBg.png";
import {useDispatch, useSelector} from "react-redux";
import {useCallback, useEffect, useState} from "react";
import {clearUserInfo, fetchUserInfo} from "../../store/modules/user";
import {removeTokenKey} from "../../util/token";
import {getChatsAPI, getTopUsersAPI} from "../../apis/user";
import userInfoProcess from "../../util/userInfoProcess";
const { Header, Sider } = Layout;


const items = [
  {
    label: "Chat",
    key: "/",
    icon: <FormOutlined />,
  },
  {
    label: "Profile",
    key: "/profile",
    icon: <EditOutlined />,
  },
];

const ClientLayout = () => {
  const nav = useNavigate();
  const dispatch = useDispatch();
  const currentUser = useSelector((state) => state.user);
  function onMenuClick(route) {
    nav(route.key);
  }

  function logoClick() {
    nav("/");
  }

  useEffect(() => {
      dispatch(fetchUserInfo());
  },[dispatch]);

  const location = useLocation();
  const selectedKey = location.pathname;

  function onLogout() {
    removeTokenKey();
    localStorage.removeItem("userId");
    clearUserInfo();
    nav("/login");
  }


  return (
    <Layout className="h-svh overflow-none">
      <Header className="h-24 p-0 flex items-start">
        {/* <span> */}
        <img
          draggable="false"
          src={logo}
          width={170}
          height={50}
          alt="LOGO"
          className="ml-6 mt-6 flex items-center justify-center"
          onClick={logoClick}
        />
        <div className="m-2 mt-6 ml-auto flex items-center">

            <span className="text-white mr-4">{currentUser.userInfo.username === undefined? "login to continue": "Hi! "+currentUser.userInfo.username}</span>


          <span className="text-white mr-4">
            <Popconfirm

              title="log out？"
              okText="Yes"
              cancelText="No"
              
              onConfirm={onLogout}
            >
              <LogoutOutlined /> logout
            </Popconfirm>
          </span>
        </div>
        {/* </span> */}
      </Header>
      <Layout>
        <Sider>
          <Menu
            mode="inline"
            theme="dark"
            items={items}
            selectedKeys={selectedKey}
            onClick={onMenuClick}
          ></Menu>
        </Sider>
        <Layout className="h-full bg-slate-800">
          {/*the position of the outlet determines the position of the child component being rendered*/}
          <Outlet />
        </Layout>
      </Layout>
    </Layout>
  );
};

export default ClientLayout;
