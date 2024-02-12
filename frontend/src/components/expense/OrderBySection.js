export default function OrderBySection({onOrderClick, amountFlag, dateFlag}) {
    return (
        <div style={{border: "1px solid black"}}>
            <ul style={{listStyle: 'none'}}>
                <li style={{display: 'inline'}}>
                    <button onClick={() => onOrderClick('date')}>
                        Order by date {dateFlag ? 'Increase' : 'Decrease'}
                    </button>
                </li>
                <li style={{display: 'inline'}}>
                    <button onClick={() => onOrderClick('amount')}>
                        Order by amount {amountFlag ? 'Increase' : 'Decrease'}
                    </button>
                </li>
            </ul>
        </div>
    );
}