function getUserInfo() {
    fetch('/admin/userinfo')
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

const renderUsers = (users) => {
    let output ='';
    users.forEach(user => {
        output += `
            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.surname}</td>
                <td>${user.email}</td>
                <td>${user.roles.map(role => " " + role.role)
                            .reduce((result, value) => result + value)    }</td>
                <td>
                    <button type="button" class="btn btn-info" data-toggle="modal"
                            data-target="#edit-modal"
                            onclick="getEditModal(${user.id})">
                        Edit
                    </button>
                </td>
                <td>
                    <button type="button" class="btn btn-danger" data-toggle="modal"
                            data-target="#delete-modal"
                            onclick="getDeleteModal(${user.id})">
                        Delete
                    </button>
                </td>
            </tr>
            `
    });
    document.getElementById('users-table').innerHTML = output;
}

function getAllUsers() {
    fetch('/admin/users')
        .then((response) => response.json())
        .then((users) => renderUsers(users));
}

getAllUsers()

let new_role = document.querySelector('#new-role').selectedOptions;
function addNewUser() {
    event.preventDefault();
    let listOfRole = [];
    for (let i = 0; i < new_role.length; i++) {
        listOfRole.push({
            role:new_role[i].value,
        });
    }
    fetch('/admin/users', {
        method:"POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify({
            name: document.getElementById('new-name').value,
            surname: document.getElementById('new-lastname').value,
            email: document.getElementById('new-email').value,
            password: document.getElementById('new-password').value,
            roles: listOfRole
        })
    })
        .then(() => {
            document.getElementById('home-tab').click();
            document.getElementById('form-add-new-user').reset();
            getAllUsers();

        })
}

document.getElementById('form-add-new-user').addEventListener('submit', addNewUser);

function getDeleteModal(id) {
    let delete_role = document.querySelector('#delete-role').getElementsByTagName('option');

    fetch('/admin/users/' + id)
        .then((res) => res.json())
        .then((user) => {
            document.getElementById('delete-id').value = user.id;
            document.getElementById('delete-name').value = user.name;
            document.getElementById('delete-lastname').value = user.surname;
            document.getElementById('delete-email').value = user.email;

            for (let i = 0; i < delete_role.length; i++) {
                delete_role[i].selected = false;
                for (let j = 0; j < user.roles.length; j++) {
                    if (delete_role[i].value === user.roles[j].role) {
                        delete_role[i].selected = true;
                    }
                }
            }
        })
}

function getEditModal(id) {

    let edit_role_selected = document.querySelector('#edit-role').getElementsByTagName('option');

    fetch('/admin/users/' + id)
        .then((res) => res.json())
        .then((user) => {
            document.getElementById('edit-id').value = user.id;
            document.getElementById('edit-name').value = user.name;
            document.getElementById('edit-lastname').value = user.surname;
            document.getElementById('edit-email').value = user.email;
            document.getElementById('edit-password').value;

            for (let i = 0; i < edit_role_selected.length; i++) {
                edit_role_selected[i].selected = false;
                for (let j = 0; j < user.roles.length; j++) {
                    if (Number (edit_role_selected[i].value) === user.roles[j].id) {
                        edit_role_selected[i].selected = true;
                    }
                }
            }
        })
}

function deleteUser() {
    event.preventDefault();
    let urlDelete = '/admin/users/' + document.getElementById('delete-id').value;
    fetch(urlDelete, {
        method:"DELETE",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
    })
        .then(() => {
            document.getElementById('delete-close').click();
            getAllUsers();
        })
}

document.getElementById('delete-form').addEventListener('submit', deleteUser);

let edit_role = document.querySelector('#edit-role').selectedOptions;

function editUser() {
    event.preventDefault();
    let listOfRole = [];
    for (let i = 0; i < edit_role.length; i++) {
        listOfRole.push({
            role: edit_role[i].value,
        });
    }

    let urlEdit = '/admin/users/' + document.getElementById('edit-id').value;
    fetch(urlEdit, {
        method:"PATCH",
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify({
            id: document.getElementById('edit-id').value,
            name: document.getElementById('edit-name').value,
            surname: document.getElementById('edit-lastname').value,
            email: document.getElementById('edit-email').value,
            password: document.getElementById('edit-password').value,
            roles: listOfRole
        })
    })
        .then(() => {
            document.getElementById('edit-close').click();
            getAllUsers();
        })
}

document.getElementById('edit-form').addEventListener('submit', editUser);


function getRoles() {
    fetch('/admin/roles')
        .then((response) => response.json())
        .then((roles) => {
            let roleSelectEdit = document.getElementById('edit-role');
            let roleSelectDelete = document.getElementById('delete-role');
            let roleSelectNew = document.getElementById('new-role');

            roleSelectEdit.innerHTML = '';
            roleSelectDelete.innerHTML = '';
            roleSelectNew.innerHTML = '';

            roles.forEach(role => {
                let optionForEdit = document.createElement('option');
                optionForEdit.value = role;
                optionForEdit.textContent = role;
                roleSelectEdit.appendChild(optionForEdit);

                let optionForDelete = document.createElement('option');
                optionForDelete.value = role;
                optionForDelete.textContent = role;
                roleSelectDelete.appendChild(optionForDelete);

                let optionForNew = document.createElement('option');
                optionForNew.value = role;
                optionForNew.textContent = role;
                roleSelectNew.appendChild(optionForNew);
            });
        })
        .catch(error => console.error('Ошибка при получении ролей:', error));
}

getRoles();