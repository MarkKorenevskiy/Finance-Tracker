'use client'
import {Fragment} from "react";
import Link from "next/link";
import {useFormState} from "react-dom";
import {updateAccountInfo} from "@/actions/serverActions";

const initialState = {
    name: '',
    balance: ''
}

export default function Edit({params}) {

    const accountId = params.id;

    const [state, formAction] = useFormState(updateAccountInfo, initialState);

    return (
        <Fragment>
            <h1>Account edit page</h1>
            <p>{accountId}</p>
            <form action={formAction}>
                <label>Name: </label>
                <input type="text" name='name'/>
                <span>{state?.name}</span>
                <br/>
                <label>Balance: </label>
                <input type="text" name='balance' defaultValue={0}/>
                <span>{state?.balance}</span>
                <br/>
                <input type="text" name={'id'} value={accountId} hidden readOnly/>
                <button type="submit">Edit account info</button>
                <Link href={`/account/${accountId}`}>Back to account page</Link>
            </form>
        </Fragment>
    );
}