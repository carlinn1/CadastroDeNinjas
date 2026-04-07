(function () {
    var themeToggle = document.getElementById("theme-toggle");
    var feedback = document.getElementById("api-feedback");
    var singleResult = document.getElementById("single-result");
    var getByIdForm = document.getElementById("get-by-id-form");
    var createForm = document.getElementById("create-form");
    var updateForm = document.getElementById("update-form");
    var deleteForm = document.getElementById("delete-form");
    var loadForUpdateButton = document.getElementById("load-for-update");
    var refreshButton = document.getElementById("refresh-list");
    var editButtons = document.querySelectorAll(".edit-ninja-btn");
    var themeStorageKey = "ninjas-theme";
    var updateIdInput = document.getElementById("update-id");
    var updateNomeInput = document.getElementById("update-nome");
    var updateEmailInput = document.getElementById("update-email");
    var updateImgInput = document.getElementById("update-img");
    var updateRankInput = document.getElementById("update-rank");
    var updateIdadeInput = document.getElementById("update-idade");
    var lastFetchedNinja = null;

    if (!feedback || !singleResult) {
        return;
    }

    function getPreferredTheme() {
        var savedTheme = localStorage.getItem(themeStorageKey);
        if (savedTheme === "dark" || savedTheme === "light") {
            return savedTheme;
        }

        var prefersDark = window.matchMedia && window.matchMedia("(prefers-color-scheme: dark)").matches;
        return prefersDark ? "dark" : "light";
    }

    function applyTheme(theme) {
        document.documentElement.setAttribute("data-theme", theme);
        if (themeToggle) {
            themeToggle.textContent = theme === "dark" ? "Tema: Escuro" : "Tema: Claro";
        }
    }

    function initThemeToggle() {
        var initialTheme = getPreferredTheme();
        applyTheme(initialTheme);

        if (!themeToggle) {
            return;
        }

        themeToggle.addEventListener("click", function () {
            var currentTheme = document.documentElement.getAttribute("data-theme") || "light";
            var nextTheme = currentTheme === "dark" ? "light" : "dark";
            localStorage.setItem(themeStorageKey, nextTheme);
            applyTheme(nextTheme);
        });
    }

    function setFeedback(message, type) {
        feedback.textContent = message;
        feedback.className = "api-feedback " + (type || "info");
    }

    function escapeHtml(value) {
        return String(value)
            .replace(/&/g, "&amp;")
            .replace(/</g, "&lt;")
            .replace(/>/g, "&gt;")
            .replace(/\"/g, "&quot;")
            .replace(/'/g, "&#039;");
    }

    function renderNinja(ninja) {
        if (!ninja) {
            singleResult.innerHTML = "<p>Ninja nao encontrado.</p>";
            return;
        }

        var nome = escapeHtml(ninja.nome || "Sem nome");
        var rank = escapeHtml(ninja.rank || "Sem rank");
        var email = escapeHtml(ninja.email || "Sem email");
        var idade = escapeHtml(ninja.idade || "-");
        var id = escapeHtml(ninja.id || "-");

        singleResult.innerHTML =
            "<p><strong>ID:</strong> " + id + "</p>" +
            "<p><strong>Nome:</strong> " + nome + "</p>" +
            "<p><strong>Rank:</strong> " + rank + "</p>" +
            "<p><strong>Email:</strong> " + email + "</p>" +
            "<p><strong>Idade:</strong> " + idade + "</p>" +
            "<button id=\"load-single-to-update\" type=\"button\" class=\"btn btn-ghost btn-small\">Carregar para PUT</button>";
    }

    function fillUpdateForm(ninja) {
        if (!ninja || !updateForm) {
            return;
        }

        updateIdInput.value = ninja.id || "";
        updateNomeInput.value = ninja.nome || "";
        updateEmailInput.value = ninja.email || "";
        updateImgInput.value = ninja.img_url || "";
        updateRankInput.value = ninja.rank || "";
        updateIdadeInput.value = ninja.idade || "";

        updateNomeInput.focus();
        updateForm.scrollIntoView({ behavior: "smooth", block: "center" });
    }

    function ninjaFromButtonDataset(button) {
        return {
            id: Number(button.getAttribute("data-id") || 0),
            nome: button.getAttribute("data-nome") || "",
            email: button.getAttribute("data-email") || "",
            img_url: button.getAttribute("data-img") || "",
            rank: button.getAttribute("data-rank") || "",
            idade: Number(button.getAttribute("data-idade") || 0)
        };
    }

    async function request(url, options) {
        var response = await fetch(url, options || {});
        var text = await response.text();

        if (!response.ok) {
            throw new Error(text || "Falha na requisicao.");
        }

        return text;
    }

    async function getNinjaById(id) {
        var response = await fetch("/listar/" + id);
        if (!response.ok) {
            if (response.status === 404) {
                throw new Error("Ninja nao encontrado.");
            }
            throw new Error("Erro ao buscar ninja por ID.");
        }

        var contentType = response.headers.get("content-type") || "";
        if (contentType.indexOf("application/json") === -1) {
            return null;
        }

        return response.json();
    }

    function payloadFromForm(form) {
        var formData = new FormData(form);
        return {
            nome: String(formData.get("nome") || "").trim(),
            email: String(formData.get("email") || "").trim(),
            img_url: String(formData.get("img_url") || "").trim(),
            rank: String(formData.get("rank") || "").trim(),
            idade: Number(formData.get("idade") || 0)
        };
    }

    if (getByIdForm) {
        getByIdForm.addEventListener("submit", async function (event) {
            event.preventDefault();
            var id = Number(new FormData(getByIdForm).get("id"));

            if (!id || id < 1) {
                setFeedback("Informe um ID valido para busca.", "error");
                return;
            }

            try {
                setFeedback("Buscando ninja...", "info");
                var ninja = await getNinjaById(id);
                lastFetchedNinja = ninja;
                renderNinja(ninja);
                setFeedback("GET concluido com sucesso.", "success");
            } catch (error) {
                lastFetchedNinja = null;
                renderNinja(null);
                setFeedback(error.message, "error");
            }
        });
    }

    if (singleResult) {
        singleResult.addEventListener("click", function (event) {
            if (event.target && event.target.id === "load-single-to-update") {
                if (!lastFetchedNinja) {
                    setFeedback("Busque um ninja antes de carregar para PUT.", "error");
                    return;
                }

                fillUpdateForm(lastFetchedNinja);
                setFeedback("Formulario de PUT preenchido. Edite e clique em Atualizar ninja.", "info");
            }
        });
    }

    if (createForm) {
        createForm.addEventListener("submit", async function (event) {
            event.preventDefault();
            var payload = payloadFromForm(createForm);

            try {
                setFeedback("Criando ninja...", "info");
                var message = await request("/criar", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(payload)
                });

                setFeedback(message || "Ninja criado com sucesso.", "success");
                createForm.reset();
                setTimeout(function () {
                    window.location.reload();
                }, 700);
            } catch (error) {
                setFeedback(error.message, "error");
            }
        });
    }

    if (updateForm) {
        updateForm.addEventListener("submit", async function (event) {
            event.preventDefault();
            var formData = new FormData(updateForm);
            var id = Number(formData.get("id"));

            if (!id || id < 1) {
                setFeedback("Informe um ID valido para atualizar.", "error");
                return;
            }

            var payload = {
                nome: String(formData.get("nome") || "").trim(),
                email: String(formData.get("email") || "").trim(),
                img_url: String(formData.get("img_url") || "").trim(),
                rank: String(formData.get("rank") || "").trim(),
                idade: Number(formData.get("idade") || 0)
            };

            try {
                setFeedback("Atualizando ninja...", "info");
                var message = await request("/atualizar/" + id, {
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(payload)
                });

                setFeedback(message || "Ninja atualizado com sucesso.", "success");
                setTimeout(function () {
                    window.location.reload();
                }, 700);
            } catch (error) {
                setFeedback(error.message, "error");
            }
        });
    }

    if (loadForUpdateButton) {
        loadForUpdateButton.addEventListener("click", async function () {
            var id = Number(updateIdInput.value);

            if (!id || id < 1) {
                setFeedback("Informe um ID valido no formulario de PUT para carregar os dados.", "error");
                return;
            }

            try {
                setFeedback("Carregando dados para PUT...", "info");
                var ninja = await getNinjaById(id);
                fillUpdateForm(ninja);
                setFeedback("Dados carregados. Agora edite o que quiser e clique em Atualizar ninja.", "success");
            } catch (error) {
                setFeedback(error.message, "error");
            }
        });
    }

    if (editButtons && editButtons.length > 0) {
        editButtons.forEach(function (button) {
            button.addEventListener("click", function () {
                var ninja = ninjaFromButtonDataset(button);
                if (!ninja.id) {
                    setFeedback("Nao foi possivel carregar os dados deste ninja para edicao.", "error");
                    return;
                }

                fillUpdateForm(ninja);
                setFeedback("Dados carregados no PUT. Edite e clique em Atualizar ninja.", "success");
            });
        });
    }

    if (deleteForm) {
        deleteForm.addEventListener("submit", async function (event) {
            event.preventDefault();
            var id = Number(new FormData(deleteForm).get("id"));

            if (!id || id < 1) {
                setFeedback("Informe um ID valido para deletar.", "error");
                return;
            }

            var shouldDelete = window.confirm("Tem certeza que deseja deletar o ninja de ID " + id + "?");
            if (!shouldDelete) {
                return;
            }

            try {
                setFeedback("Deletando ninja...", "info");
                var message = await request("/deletar/" + id, {
                    method: "DELETE"
                });

                setFeedback(message || "Ninja deletado com sucesso.", "success");
                deleteForm.reset();
                setTimeout(function () {
                    window.location.reload();
                }, 700);
            } catch (error) {
                setFeedback(error.message, "error");
            }
        });
    }

    if (refreshButton) {
        refreshButton.addEventListener("click", function () {
            window.location.reload();
        });
    }

    initThemeToggle();
})();
