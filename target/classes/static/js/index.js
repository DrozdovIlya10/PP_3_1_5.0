let roleList = [{id: 1, role: "ROLE_USER"}, {id: 2, role: "ROLE_ADMIN"}]
getUsers();

const active1 = document.querySelector('#admin-tab');
const active2 = document.querySelector('#Admin');
const active3 = document.querySelector('#user-tab');
const active4 = document.querySelector('#User');
fetch(`/user`)
    .then(res => res.json())
    .then(user => {
        let role_user;
        let theUser = true;
        for (let i = 0; i < user.roles.length; i++) {
            role_user = user.roles[i].name
            if (role_user === "ROLE_ADMIN") {
                theUser = false;
            }
        }
        //const role_user = user.roles.map(e => " " + e.name);
        //console.log(role_user)
        if (theUser) {
            active3.classList.toggle('active');
            active4.classList.toggle('show');
            active4.classList.toggle('active');
        } else {
            active1.classList.toggle('active');
            active2.classList.toggle('show');
            active2.classList.toggle('active');
        }
    })

fetch(`/user`)
    .then(res => res.json())
    .then(user => {
        document.querySelector('#info_name').innerHTML = `
             ${user.email}
        `;
        document.querySelector('#info_role').innerHTML = `
            ${user.roles.map(e => " " + e.name.substr(5))}
        `;
        document.querySelector('#tabUser').innerHTML = `
            <tr>
                <td>${user.id}</td>     
                <td>${user.username}</td>
                <td>${user.lastname}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>
                <td>${user.roles.map(e => " " + e.name.substr(5))}</td>                   
            </tr>
        `;
    })

async function getUsers() {
    let table = '';
    await fetch('http://localhost:8080/users')
        .then(res => res.json())
        .then(users => {
            users.forEach(user => {
                table += `
                <tr>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.lastname}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>
                <td>${user.roles.map(e => " " + e.name.substr(5))}</td>
                <td>
                    <a type="button " class="btn btn-sm btn-info text-white" data-bs-toggle="modal"
                        data-bs-target="#editModal" id="editButton" onclick="Edit(${user.id})">Edit</a>
                </td>
                <td>
                    <a type="button" class="btn btn-sm btn-danger text-white" data-bs-toggle="modal"
                        data-bs-target="#delete" onclick="Delete(${user.id})" >Delete</a>
                </td>
                </tr>
           `
                document.querySelector('#tableAllUser').innerHTML = table;
            })
        })
}

function Edit(id) {
    fetch('http://localhost:8080/users')
        .then(res => res.json())
        .then(users => {
            users.forEach(user => {
                if (user.id === id) {
                    document.getElementById('id0').value = user.id;
                    document.getElementById('name0').value = user.username;
                    document.getElementById('lastname0').value = user.lastname;
                    document.getElementById('age0').value = user.age;
                    document.getElementById('email0').value = user.email;
                    document.getElementById('password0').value = user.password;
                    document.getElementById('roles0')
                        .getElementsByTagName('option').value = user.roles.map(e => " " + e.id)
                    document.getElementById('buttonEdit')
                        .setAttribute('onclick', 'EditUser(' + user.id + ')');
                }
            })
        })
}

function Delete(id) {
    fetch('http://localhost:8080/users')
        .then(res => res.json())
        .then(users => {
            users.forEach(user => {
                if (user.id === id) {
                    document.getElementById('id1').value = user.id;
                    document.getElementById('name1').value = user.username;
                    document.getElementById('lastname1').value = user.lastname;
                    document.getElementById('age1').value = user.age;
                    document.getElementById('email1').value = user.email;
                    document.getElementById('roles1').onselect = user.roles.map(e => " " + e.id)
                    document.getElementById('buttonDelete')
                        .setAttribute('onclick', 'DeleteUser(' + user.id + ')');

                }
            })
        })
}

const userFetch = {
    head: {
        'Accept': 'application/json', 'Content-Type': 'application/json', 'Referer': null
    },
    addNewUser: async (user) => await fetch('/users', {
        method: 'POST', headers: userFetch.head, body: JSON.stringify(user)
    }),
    deleteUser: async (id) => await fetch('/user/' + id, {method: 'DELETE', headers: userFetch.head}),
    editUser: async (user, id) => await fetch('/user/' + id, {
        method: 'PUT', headers: userFetch.head, body: JSON.stringify(user)
    })

}

async function AddUser() {
    username = document.getElementById('addName').value;
    lastname = document.getElementById('addLastname').value;
    age = document.getElementById('addAge').value;
    email = document.getElementById('addEmail').value;
    password = document.getElementById('addPassword').value;
    let checkedRoles = () => {
        let array = []
        let options = document.querySelector('#addRoles').options
        for (let i = 0; i < options.length; i++) {
            if (options[i].selected) {
                array.push(roleList[i])
            }
        }
        return array;
    }
    let data = {
        "username": username,
        "lastname": lastname,
        "age": age,
        "email": email,
        "password": password,
        "roles": checkedRoles()
    }

    document.getElementById('formNewUser');
    event.preventDefault()
    await userFetch.addNewUser(data);

    console.log('Отправка!');

    await getUsers();

    document.getElementById('addName').value = '';
    document.getElementById('addLastname').value = '';
    document.getElementById('addAge').value = '';
    document.getElementById('addEmail').value = '';
    document.getElementById('addPassword').value = '';

    $('.nav-tabs a[ data-bs-target="#nav-users"]').tab('show');

}

async function DeleteUser(id) {

    const form = document.getElementById('formDelete');
    event.preventDefault()
    await userFetch.deleteUser(id);
    await getUsers();
    $('#delete').modal('hide')
}

async function EditUser(id) {

    username0 = document.getElementById('name0').value;
    lastname0 = document.getElementById('lastname0').value;
    age0 = document.getElementById('age0').value;
    email0 = document.getElementById('email0').value;
    password0 = document.getElementById('password0').value;
    let checkedRoles = () => {
        let array = []
        let options = document.querySelector('#roles0').options
        for (let i = 0; i < options.length; i++) {
            if (options[i].selected) {
                array.push(roleList[i])
            }
        }
        return array;
    }

    let data = {
        "username": username0,
        "lastname": lastname0,
        "age": age0,
        "email": email0,
        "password": password0,
        "roles": checkedRoles()
    }

    document.getElementById('EditForm');
    event.preventDefault()
    await userFetch.editUser(data, id);
    await getUsers();
    $('#editModal').modal('hide');
}
