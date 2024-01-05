import React from "react";

export function Table() {
    let number = 2147483647
    return (
        <table className={"rating-table"}>
            <thead>
            <tr>
                <th>№</th>
                <th style={{width: "200px"}}>Игрок</th>
                <th style={{width: "80px"}}>Вес</th>
                <th style={{width: "130px"}}>Рейтинг</th>
                <th style={{width: "200px"}}>Последняя игра</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>1</td>
                <td>ИВВВИВВВИВВВИВВВ</td>
                <td>999</td>
                <td>{number.toLocaleString()}</td>
                <td>3 недели назад</td>
            </tr>
            <tr>
                <td>1</td>
                <td>Twitter</td>
                <td>732</td>
                <td>10437</td>
                <td>00:51:22</td>
            </tr>
            <tr>
                <td>1</td>
                <td>Amazon</td>
                <td>416</td>
                <td>5327</td>
                <td>00:24:34</td>
            </tr>
            <tr>
                <td>1</td>
                <td>LinkedIn</td>
                <td>365</td>
                <td>296</td>
                <td>00:12:10</td>
            </tr>
            <tr>
                <td>1</td>
                <td>CodePen</td>
                <td>200</td>
                <td>4135</td>
                <td>00:46:19</td>
            </tr>
            <tr>
                <td>99999999</td>
                <td>GitHub</td>
                <td>4623</td>
                <td>3486</td>
                <td>00:31:52</td>
            </tr>
            <tr>
                <td>99999999</td>
                <td>GitHub</td>
                <td>4623</td>
                <td>3486</td>
                <td>00:31:52</td>
            </tr>
            <tr>
                <td>99999999</td>
                <td>GitHub</td>
                <td>4623</td>
                <td>3486</td>
                <td>00:31:52</td>
            </tr>
            <tr>
                <td>99999999</td>
                <td>GitHub</td>
                <td>4623</td>
                <td>3486</td>
                <td>00:31:52</td>
            </tr>
            <tr>
                <td>99999999</td>
                <td>GitHub</td>
                <td>4623</td>
                <td>3486</td>
                <td>00:31:52</td>
            </tr>
            <tr>
                <td>99999999</td>
                <td>GitHub</td>
                <td>4623</td>
                <td>3486</td>
                <td>00:31:52</td>
            </tr>
            <tr>
                <td>99999999</td>
                <td>GitHub</td>
                <td>4623</td>
                <td>3486</td>
                <td>00:31:52</td>
            </tr>
            <tr>
                <td>99999999</td>
                <td>GitHub</td>
                <td>4623</td>
                <td>3486</td>
                <td>00:31:52</td>
            </tr>
            <tr>
                <td>99999999</td>
                <td>GitHub</td>
                <td>4623</td>
                <td>3486</td>
                <td>00:31:52</td>
            </tr>
            </tbody>
        </table>
    )
}