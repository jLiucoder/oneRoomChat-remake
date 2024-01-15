import { getTokenKey } from "../util/token";
import { Navigate } from "react-router-dom";

export function AuthRoutes({ children }) {
    const token = getTokenKey();
    if (token) {
        return <>{children}</>;
    } else {
        return <Navigate to={"/login"} replace />;
    }
}