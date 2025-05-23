import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
import os

# Gráfico de consumo de energia (já existente)
if os.path.exists("consumo.csv"):
    consumo = pd.read_csv("consumo.csv")
    plt.figure(figsize=(10, 5))
    plt.plot(consumo["Minuto"], consumo["kWh"], marker='o', linestyle='-')
    plt.title("Consumo de Energia por Minuto")
    plt.xlabel("Minuto")
    plt.ylabel("Energia Consumida (kWh)")
    plt.grid(True)
    plt.tight_layout()
    plt.savefig("grafico_consumo.png")
    plt.show()
else:
    print("Arquivo consumo.csv não encontrado.")

# Gráfico de tempo médio de espera por minuto
if os.path.exists("espera.csv"):
    espera = pd.read_csv("espera.csv")
    # Simula que cada pessoa foi gerada em um minuto sequencial
    espera["Minuto"] = espera["Pessoa"]  # Ou ajuste conforme sua lógica real

    # Agrupa por minuto e calcula média de espera por minuto
    espera_por_minuto = espera.groupby("Minuto")["TempoEspera"].mean().reset_index()

    plt.figure(figsize=(10, 5))
    plt.plot(espera_por_minuto["Minuto"], espera_por_minuto["TempoEspera"], marker='o', linestyle='-')
    plt.title("Tempo Médio de Espera por Minuto")
    plt.xlabel("Minuto")
    plt.ylabel("Tempo Médio de Espera (min)")
    plt.grid(True)
    plt.tight_layout()
    plt.savefig("grafico_espera_por_minuto.png")
    plt.show()
else:
    print("Arquivo espera.csv não encontrado.")