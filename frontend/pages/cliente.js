const clienteAPI = "http://localhost:8080/api/clientes";

document.getElementById("cliente-form").addEventListener("submit", async function (e) {
  e.preventDefault();
  const cliente = {
    nome: nome.value,
    cpf: cpf.value,
    email: email.value,
    dataNascimento: dataNascimento.value
  };
  const res = await fetch(clienteAPI, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(cliente)
  });
  if (res.ok) {
    alert("Cliente cadastrado.");
    this.reset();
    carregarClientes();
  } else {
    alert("Erro ao cadastrar cliente.");
  }
});

async function carregarClientes() {
  const tabela = document.querySelector("#clientes-tabela tbody");
  tabela.innerHTML = "";
  const res = await fetch(clienteAPI);
  const clientes = await res.json();
  clientes.forEach(c => {
    tabela.innerHTML += `<tr><td>${c.id}</td><td>${c.nome}</td><td>${c.cpf}</td><td>${c.email}</td><td>${c.dataNascimento}</td></tr>`;
  });
}
carregarClientes();
