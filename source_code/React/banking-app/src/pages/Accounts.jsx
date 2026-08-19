import { useState } from "react";
import AccountDetails from "../components/AccountDetails";

function Accounts() {
    const [accountId] = useState(
        () => Math.floor(Math.random() * 100)
    );

    return (
        <div>
            <h1>Accounts</h1>

            <AccountDetails accountId={accountId} />
        </div>
    );
}

export default Accounts;
