import {createBrowserRouter} from "react-router-dom";
import Layout from "../pages/Layout";
import Chat from "../pages/Chat";
import Profile from "../pages/Profile";
import Login from "../pages/Login";
import {AuthRoutes} from "../components/AuthRoutes";
import Signup from "../pages/Signup";
import NotFound from "../pages/NotFound";

const router = createBrowserRouter([
    {
        path: "/",
        element: (
            <AuthRoutes>
                <Layout/>
            </AuthRoutes>),
        children: [
            {
                index: true,
                element: <Chat/>,
            },
            {
                path: "profile",
                element: <Profile/>,

            },
        ],
    },
    {
        path: "/login",
        element: <Login/>,
    },
    {
        path: "/signup",
        element: <Signup/>,
    },
    {
        path:"*",
        element:<NotFound/>,
    }

]);

export default router;
