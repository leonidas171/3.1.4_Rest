function getUserInfo() {
    fetch('/user/userinfo')
        .then((response) => response.json())
        .then((user) => {
            let roles = user.roles
                            .map(role => " " + role.role)
                            .reduce((result, value) => result + value);
            document.getElementById('head-username').innerHTML += user.email;
            document.getElementById('head-role').innerHTML += roles;
            document.getElementById('user-info').innerHTML += `
                <tr>
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.surname}</td>
                    <td>${user.email}</td>
                    <td>${roles}</td>
                </tr>
            `;
        });
}

getUserInfo();