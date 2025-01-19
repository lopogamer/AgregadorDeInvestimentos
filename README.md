# API de Gestão de Investimentos  

![Java](https://img.shields.io/badge/Java-21-blue)  
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-green)  
![MySQL](https://img.shields.io/badge/MySQL-Docker-orange)  
![JUnit](https://img.shields.io/badge/Tests-JUnit-red)  

Uma API RESTful desenvolvida em Java com Spring Boot para gerenciar usuários, contas e investimentos em ações. A aplicação permite a integração com a API Brapi para validação e consulta de ações, cálculo de valores investidos, e oferece suporte a múltiplas contas por usuário.  

---

## 🚀 **Funcionalidades**  

- **Gerenciamento de Usuários:**  
  CRUD completo para criar, listar, atualizar e deletar usuários.  
- **Gerenciamento de Contas:**  
  Cada usuário pode ter múltiplas contas vinculadas.  
- **Gerenciamento de Ações (Stocks):**  
  Cada conta pode conter várias ações com os seguintes recursos:  
  - Validação do nome da ação usando a API Brapi.  
  - Consulta do preço atual (regularMarketPrice).  
  - Cálculo do valor total investido em cada ação.  
- **Testes:**  
  - Testes de integração com **Insomnia**.  
  - Testes unitários em desenvolvimento usando **JUnit** e **Mockito**.  

---

## 🛠️ **Tecnologias Utilizadas**  

- **Java 21**  
- **Spring Boot 3.3.7**  
- **MySQL** (gerenciado com Docker)  
- **API Externa:** [Brapi](https://brapi.dev/)  
- **Ferramentas de Teste:** Insomnia, JUnit, Mockito  

---
