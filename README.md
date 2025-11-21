# Comparación Empírica de Algoritmos de Ordenación  
### *BubbleSort · SelectionSort · InsertionSort*

![Java](https://img.shields.io/badge/Java-21-blue?style=for-the-badge)
![Maven](https://img.shields.io/badge/Maven-Project-orange?style=for-the-badge)
![Datasets](https://img.shields.io/badge/Datasets-CSV-green?style=for-the-badge)

---

## Descripción del Proyecto

Este proyecto implementa un sistema completo para:

- Generar datasets reproducibles en formato **CSV**
- Ordenar varios tipos de registros usando:
  - **BubbleSort**
  - **SelectionSort**
  - **InsertionSort**
- Ejecutar un **benchmark** para comparar el rendimiento de los algoritmos

---

# Generación de Datasets

Los datasets se guardan en:

src/main/resources/datasets/

### Archivos Generados

| Archivo | Registros | Tipo |
|--------|-----------|---------------------|
| `citas_100.csv` | 100 | Aleatorio |
| `citas_100_casi_ordenadas.csv` | 100 | 80% ordenado |
| `pacientes_500.csv` | 500 | Aleatorio |
| `inventario_500_inverso.csv` | 500 | Totalmente invertido |

### Características de todos los CSV

- Codificación: **UTF-8 sin BOM**
- Separador: **`;`**
- Encabezado en la primera fila
- Fechas en formato ISO 8601 (`YYYY-MM-DDTHH:MM`)
- Usan siempre semilla: **42**

---

# Arquitectura del Proyecto

```java
src/
└── main/
├── java/
│ ├── config/
│ │ └── Config.java
│ ├── models/
│ │ ├── Cita.java
│ │ ├── Paciente.java
│ │ └── ItemInventario.java
│ ├── generator/
│ │ └── GeneradorDatasets.java
│ ├── sorting/
│ │ ├── BubbleSort.java
│ │ ├── SelectionSort.java
│ │ └── InsertionSort.java
│ ├── benchmark/
│ │ └── Benchmark.java
│ └── App.java
└── resources/
└── datasets/
```

### Justificación de arquitectura (SOLID)

| Paquete | Responsabilidad |
|---------|------------------|
| `models/` | Modelos de datos (POJO o Records) |
| `sorting/` | Algoritmos de ordenación |
| `generator/` | Creación de datasets CSV |
| `benchmark/` | Mediciones y comparaciones |
| `config/` | Constantes globales |
| `App.java` | Punto de entrada del sistema |

---

# Cómo Ejecutar

### Requisitos
- Java 21  
- Maven instalado

### Compilar
```bash
mvn clean package

### Al ejecutar

- Se generan los 4 datasets.
- Se ejecuta el benchmark.
- Los resultados aparecen en consola.

---

## Metodología del Benchmark
Cada algoritmo se ejecuta sobre copias de los datasets.
Se realizan R repeticiones.
Se descartan las primeras 3.
Se obtiene la mediana.
Se reporta por consola:
- Tiempo en milisegundos
- Comparaciones - Intercambios
 ---
# Conclusiones Generales
- Inserción es el más eficiente en listas casi ordenadas.
- Selección mantiene un rendimiento estable sin importar el dataset.
- Burbuja es el peor en listas desordenadas e inversas.
- Los datasets influyen mucho en el comportamiento de los algoritmos.

---
