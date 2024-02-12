export default function AccountFilter({accounts, onAccountChoice}) {
    return (
        <div style={{border: '1px solid black'}}>
            <ul style={{listStyle: 'none'}}>
                <li key={'all'} style={{display: 'inline'}}>
                    <button onClick={() => onAccountChoice('all')}>
                        All
                    </button>
                </li>
                {
                    accounts.map(account => {
                        return [
                            <li key={account.id} style={{display: 'inline'}}>
                                <button onClick={() => onAccountChoice(account.id)}>
                                    {account.name}
                                </button>
                            </li>
                        ];
                    })
                }
            </ul>
        </div>
    );
}