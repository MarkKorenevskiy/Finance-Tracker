export default function CategoryFilter({categories, onCategoryChoice}) {
    return(
        <div style={{border: '1px solid black'}}>
            <ul style={{listStyle: 'none'}}>
                <li key={'all'} style={{display: 'inline'}}>
                    <button onClick={() => onCategoryChoice('all')}>
                        All
                    </button>
                </li>
                {
                    categories.map(category => {
                        return [
                            <li key={category.id} style={{display: 'inline'}}>
                                <button onClick={() => onCategoryChoice(category.id)}>
                                    {category.category}
                                </button>
                            </li>
                        ];
                    })
                }
            </ul>
        </div>
    );
}