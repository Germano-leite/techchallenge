const contratacaoAPI = "http://localhost:8080/api";
const clienteSelect = document.getElementById("clienteId");
const cartaoSelect = document.getElementById("cartaoId");
const contratacoesDiv = document.getElementById("contratacoes-lista");


async function carregarClientesECartoes() {
  const clientes = await fetch(contratacaoAPI + "/clientes").then(r => r.json());
  const cartoes = await fetch(contratacaoAPI + "/cartoes").then(r => r.json());

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

    const clienteId = document.getElementById("clienteId").value;
    const cartaoId = document.getElementById("cartaoId").value;

    // Objeto JavaScript que corresponde ao DTO esperado pela API
    const contratacaoData = {
        clienteId: parseInt(clienteId),
        cartaoId: parseInt(cartaoId)
    };

    // URL agora é apenas o endpoint, sem os parâmetros
    const url = `${contratacaoAPI}/contratacoes`;

    try {
        const res = await fetch(url, {
            method: "POST",
            // Defina o cabeçalho para indicar que você está enviando JSON
            headers: {
                "Content-Type": "application/json",
            },
            //Converta o objeto JavaScript para uma string JSON e envie no corpo
            body: JSON.stringify(contratacaoData),
        });

        if (res.ok) {
            alert("Cartão contratado com sucesso!");
            listarTodasContratacoes(); // Atualiza a lista na tela
            document.getElementById("contratacao-form").reset(); // Limpa o formulário
        } else {
            // Tenta ler a mensagem de erro da API para um feedback mais claro
            const errorData = await res.json();
            alert(`Erro ao contratar cartão: ${errorData.message || 'Erro desconhecido'}`);
        }
    } catch (error) {
        console.error("Falha na requisição:", error);
        alert("Não foi possível conectar com o servidor. Verifique sua conexão.");
    }
});

clienteSelect.addEventListener("change", () => {
  const id = clienteSelect.value;
  if (id) listarTodasContratacoes;
});

async function cancelarContratacao(id) {
  const confirmacao = confirm("Deseja realmente cancelar esta contratação?");
  if (!confirmacao) return;

    const res = await fetch(`${contratacaoAPI}/contratacoes/${id}`, {method: "DELETE"});

  if (res.ok) {
    alert("Contratação cancelada.");
    listarTodasContratacoes();
  } else {
    alert("Erro ao cancelar.");
  }
}
async function listarTodasContratacoes() {
    try {
        const res = await fetch(`${contratacaoAPI}/contratacoes`);
        if (!res.ok) {
            throw new Error(`Erro ao buscar contratações: ${res.statusText}`);
        }
        const listaDeContratacoes = await res.json();
        const tabela = document.querySelector("#contratacoes-tabela tbody");

        if (listaDeContratacoes.length === 0) {
            // Corrigido o colspan para 7, que é o número de colunas da tabela
            tabela.innerHTML = `<tr><td colspan="7">Nenhuma contratação encontrada.</td></tr>`;
            return;
        }

        // 1. Melhoria de performance: Crie a string HTML primeiro
        const linhasTabelaHtml = listaDeContratacoes.map(c => `
            <tr>
                <td>${c.id}</td>
                <td>${c.clienteNome ?? 'N/A'}</td> 
                <td>${c.cartaoNome ?? 'N/A'}</td>
                <td>${c.status}</td>
                <td>${new Date(c.dataContratacao).toLocaleDateString('pt-BR')}</td>
                <td>${c.numeroCartao ?? 'Não gerado'}</td>
                <td>
                    <button class='btn btn-danger btn-sm' onclick="cancelarContratacao(${c.id})">Cancelar</button>
                </td>
            </tr>
        `).join('');

        // 2. Atualize o HTML da tabela uma única vez
        tabela.innerHTML = linhasTabelaHtml;

    } catch (error) {
        console.error("Falha ao listar contratações:", error);
        const tabela = document.querySelector("#contratacoes-tabela tbody");
        tabela.innerHTML = `<tr><td colspan="7">Erro ao carregar os dados. Tente novamente mais tarde.</td></tr>`;
    }
}

carregarClientesECartoes();
listarTodasContratacoes();