import Link from "next/link";

export default function AddExpenseBtn() {
    return (
        <div style={{border: "1px solid black"}}>
            <Link href={"/tracker/add"}>
                <button>Add new expense</button>
            </Link>
        </div>
    );
}