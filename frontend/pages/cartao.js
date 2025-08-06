const cartaoAPI = "http://localhost:8080/cartoes";

document.getElementById("cartao-form").addEventListener("submit", async function (e) {
  e.preventDefault();
  const cartao = {
    nome: nome.value,
    tipo: tipo.value,
    anuidade: anuidade.value,
    bandeira: bandeira.value
  };
  const res = await fetch(cartaoAPI, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(cartao)
  });
  if (res.ok) {
    alert("Cartão cadastrado.");
    this.reset();
    carregarCartoes();
  } else {
    alert("Erro ao cadastrar cartão.");
  }
});

async function carregarCartoes() {
  const tabela = document.querySelector("#cartoes-tabela tbody");
  tabela.innerHTML = "";
  const res = await fetch(cartaoAPI);
  const cartoes = await res.json();
  cartoes.forEach(c => {
    tabela.innerHTML += `<tr><td>${c.id}</td><td>${c.nome}</td><td>${c.tipo}</td><td>${c.anuidade}</td><td>${c.bandeira}</td></tr>`;
  });
}
carregarCartoes();
