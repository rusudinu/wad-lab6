const form = document.querySelector("#search-form");
const input = document.querySelector("#search-input");

const postgresPanel = buildPanel("postgres", "/api/search/postgres", false);
const elasticPanel = buildPanel("elasticsearch", "/api/search/elasticsearch", true);

if (form && input && postgresPanel && elasticPanel) {
	form.addEventListener("submit", async (event) => {
		event.preventDefault();
		const query = input.value.trim();
		if (!query) {
			resetPanel(postgresPanel);
			resetPanel(elasticPanel);
			return;
		}
		setLoading(postgresPanel);
		setLoading(elasticPanel);
		await Promise.all([
			loadResults(postgresPanel, query),
			loadResults(elasticPanel, query),
		]);
	});
}

function buildPanel(key, url, showScore) {
	const list = document.querySelector(`[data-role="${key}-results"]`);
	const time = document.querySelector(`[data-role="${key}-time"]`);
	const count = document.querySelector(`[data-role="${key}-count"]`);
	if (!list || !time || !count) {
		return null;
	}
	return { key, url, showScore, list, time, count };
}

function resetPanel(panel) {
	panel.time.textContent = "-- ms";
	panel.count.textContent = "0 results";
	setPanelMessage(panel, "No results yet.", "empty");
}

function setLoading(panel) {
	panel.time.textContent = "-- ms";
	panel.count.textContent = "Searching...";
	setPanelMessage(panel, "Searching...", "empty");
}

async function loadResults(panel, query) {
	try {
		const data = await fetchJson(`${panel.url}?q=${encodeURIComponent(query)}`);
		renderPanel(panel, data);
	} catch (error) {
		panel.time.textContent = "-- ms";
		panel.count.textContent = "0 results";
		setPanelMessage(panel, "Unable to load results.", "error");
	}
}

async function fetchJson(url) {
	const response = await fetch(url, {
		headers: { Accept: "application/json" },
	});
	if (!response.ok) {
		throw new Error(`Request failed: ${response.status}`);
	}
	return response.json();
}

function renderPanel(panel, data) {
	const results = Array.isArray(data?.results) ? data.results : [];
	panel.time.textContent = formatTime(data?.executionTimeMs);
	panel.count.textContent = formatCount(results.length);
	panel.list.replaceChildren();
	if (results.length === 0) {
		setPanelMessage(panel, "No results yet.", "empty");
		return;
	}

	results.forEach((hit) => {
		panel.list.appendChild(renderResult(panel, hit));
	});
}

function renderResult(panel, hit) {
	const product = hit?.product ?? {};
	const item = document.createElement("li");
	item.className = "result-card";

	const titleRow = document.createElement("div");
	titleRow.className = "result-title";
	const title = document.createElement("span");
	title.textContent = product.title || "Untitled product";
	titleRow.appendChild(title);

	if (panel.showScore) {
		const score = document.createElement("span");
		score.className = "score";
		if (typeof hit?.relevanceScore === "number") {
			score.textContent = `Score ${hit.relevanceScore.toFixed(3)}`;
		} else {
			score.textContent = "Score --";
		}
		titleRow.appendChild(score);
	}

	item.appendChild(titleRow);

	if (product.description) {
		const description = document.createElement("p");
		description.className = "result-desc";
		description.textContent = product.description;
		item.appendChild(description);
	}

	const meta = buildMeta(product);
	if (meta.length > 0) {
		const metaRow = document.createElement("div");
		metaRow.className = "result-meta";
		meta.forEach((value) => {
			const chip = document.createElement("span");
			chip.className = "meta-chip";
			chip.textContent = value;
			metaRow.appendChild(chip);
		});
		item.appendChild(metaRow);
	}

	return item;
}

function buildMeta(product) {
	const meta = [];
	if (product.category?.name) {
		meta.push(`Category: ${product.category.name}`);
	}
	if (product.manufacturer?.name) {
		meta.push(`Maker: ${product.manufacturer.name}`);
	}
	if (product.price != null) {
		meta.push(`Price: ${product.price}`);
	}
	return meta;
}

function formatTime(value) {
	if (typeof value !== "number" || Number.isNaN(value)) {
		return "-- ms";
	}
	return `${Math.max(0, Math.round(value))} ms`;
}

function formatCount(count) {
	if (count === 1) {
		return "1 result";
	}
	return `${count} results`;
}

function setPanelMessage(panel, message, className) {
	panel.list.replaceChildren();
	const item = document.createElement("li");
	item.className = className;
	item.textContent = message;
	panel.list.appendChild(item);
}
