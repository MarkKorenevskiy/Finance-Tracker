import {redirect} from "next/navigation";
import Link from "next/link";

export default function Accounts({accounts}) {
    return (
        <div>
            <h2>Accounts</h2>
            <ul style={{listStyle: 'none', display: 'flex', flexDirection: 'row', gap: '30px'}}>
                {
                    accounts.map(account => {
                        return [
                            <li key={account.id}>
                                <AccountBox account={account}/>
                            </li>
                        ];
                    })
                }
            </ul>
        </div>
    );
}

function AccountBox({account}) {

    const formatter = new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD'
    });


    return (
        <div>
            <Link href={`/account/${account.id}`}><p>{account.name}</p></Link>
            <p>{formatter.format(account.balance)}</p>
            <Link href={`/account/edit/${account.id}`}>
                <button>Edit</button>
            </Link>
        </div>
    );
}