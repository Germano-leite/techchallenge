const api = "http://localhost:8080";
const clienteSelect = document.getElementById("clienteId");
const cartaoSelect = document.getElementById("cartaoId");
const contratacoesDiv = document.getElementById("contratacoes-lista");


async function carregarClientesECartoes() {
  const clientes = await fetch(api + "/clientes").then(r => r.json());
  const cartoes = await fetch(api + "/cartoes").then(r => r.json());

  clientes.forEach(c => {
    const option = document.createElement("option");
    option.value = c.id;
    option.textContent = `${c.nome} (${c.id})`;
    clienteSelect.appendChild(option);
  });

  cartoes.forEach(c => {
    const option = document.createElement("option");
    option.value = c.id;
    option.textContent = `${c.nome} (${c.bandeira})`;
    cartaoSelect.appendChild(option);
  });
}

document.getElementById("contratacao-form").addEventListener("submit", async function (e) {
  e.preventDefault();
  const clienteId = clienteSelect.value;
  const cartaoId = cartaoSelect.value;
  const status = document.getElementById("status").value;

  const url = `${api}/contratacoes?clienteId=${clienteId}&cartaoId=${cartaoId}&status=${status}`;
  const res = await fetch(url, {
    method: "POST"
  });

  if (res.ok) {
    alert("Cartão contratado com sucesso!");
    listarTodasContratacoes();
  } else {
    alert("Erro ao contratar cartão.");
  }
});

clienteSelect.addEventListener("change", () => {
  const id = clienteSelect.value;
  if (id) listarContratacoes(id);
});

async function cancelarContratacao(id) {
  const confirmacao = confirm("Deseja realmente cancelar esta contratação?");
  if (!confirmacao) return;

    const res = await fetch(`${api}/contratacoes/${id}`, {method: "DELETE"});

  if (res.ok) {
    alert("Contratação cancelada.");
    listarTodasContratacoes();
  } else {
    alert("Erro ao cancelar.");
  }
}

async function listarTodasContratacoes() {
  const res = await fetch(`${api}/contratacoes`);
  const lista = await res.json();
  const tabela = document.querySelector("#contratacoes-tabela tbody");
  tabela.innerHTML = "";

  if (lista.length === 0) {
    tabela.innerHTML = `<tr><td colspan="6">Nenhuma contratação encontrada.</td></tr>`;
    return;
  }

  lista.forEach(c => {
    tabela.innerHTML += `
      <tr>
        <td>${c.id}</td>
        <td>${c.cliente?.nome ?? 'N/A'}</td>
        <td>${c.cartao?.nome ?? 'N/A'}</td>
        <td>${c.status}</td>
        <td>${c.dataContratacao}</td>
        <td>
          <button class='btn btn-danger btn-sm' onclick="cancelarContratacao(${c.id})">Cancelar</button>
        </td>
      </tr>
    `;
  });
}

/*
async function listarContratacoes(clienteId) {
  const res = await fetch(`${api}/contratacoes/cliente/${clienteId}`);
  const lista = await res.json();
  console.log("Contratações:", lista);

  const tabela = document.querySelector("#contratacoes-tabela tbody");
  tabela.innerHTML = "";

  if (lista.length === 0) {
    tabela.innerHTML = `<tr><td colspan="5">Nenhuma contratação encontrada.</td></tr>`;
    return;
  }

  lista.forEach(c => {
    const linha = document.createElement("tr");
    linha.innerHTML = `
      <td>${c.id}</td>
      <td>${c.cartao?.nome ?? 'N/A'}</td>
      <td>${c.status}</td>
      <td>${c.dataContratacao}</td>
      <td>
        <button class='btn btn-danger btn-sm' onclick="cancelarContratacao(${c.id}, ${clienteId})">Cancelar</button>
      </td>
    `;
    tabela.appendChild(linha);
  });
}
*/
carregarClientesECartoes();
listarTodasContratacoes();