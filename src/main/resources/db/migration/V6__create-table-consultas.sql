CREATE TABLE consultas(
	[id][BIGINT] NOT NULL IDENTITY(1,1) PRIMARY KEY,
	[medico_id][BIGINT] NOT NULL FOREIGN KEY(medico_id) REFERENCES[medicos]([id]),
	[paciente_id][BIGINT] NOT NULL FOREIGN KEY(paciente_id) REFERENCES[pacientes]([id]),
	[data][DATETIME] NOT NULL
);