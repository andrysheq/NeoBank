NeoBank - кредитный конвеер.
Задание для прохождения практики в Neoflex, 2024г.

Логика работы : 
1.	Пользователь отправляет заявку на кредит.
2.	МС Заявка осуществляет прескоринг заявки и если прескоринг проходит, то заявка сохраняется в МС Сделка и отправляется в КК.
3.	КК возвращает через МС Заявку пользователю 4 предложения (сущность "LoanOffer") по кредиту с разными условиями (например без страховки, со страховкой, с зарплатным клиентом, со страховкой и зарплатным клиентом) или отказ.
4.	Пользователь выбирает одно из предложений, отправляется запрос в МС Заявка, а оттуда в МС Сделка, где заявка на кредит и сам кредит сохраняются в базу.
5.	МС Сделка отправляет клиенту письмо с текстом "Ваша заявка предварительно одобрена, завершите оформление".
6.	Клиент отправляет запрос в МС Сделка со всеми своими полными данными о работодателе и прописке. Происходит скоринг данных в КК, КК рассчитывает все данные по кредиту (ПСК, график платежей и тд), МС Сделка сохраняет обновленную заявку и сущность кредит сделанную на основе CreditDTO полученного из КК со статусом CALCULATED в БД.
7.	После валидации МС Сделка отправляет письмо на почту клиенту с одобрением или отказом. Если кредит одобрен, то в письме присутствует ссылка на запрос "Сформировать документы"
8.	Клиент отправляет запрос на формирование документов в МС Сделка, МС Сделка отправляет клиенту на почту документы для подписания и ссылку на запрос на согласие с условиями.
9.	Клиент может отказаться от условий или согласиться. Если согласился - МС Сделка на почту отправляет код и ссылку на подписание документов, куда клиент должен отправить полученный код в МС Сделка.
10.	Если полученный код совпадает с отправленным, МС Сделка выдает кредит (меняет статус сущности "Кредит" на ISSUED, а статус заявки на CREDIT_ISSUED)

Swagger:
  http://localhost:8001/swagger-ui/index.html#/
  ![image](https://github.com/andrysheq/NeoBank/assets/141515497/b2d38078-2ccf-4640-9d06-0f72fd2cffaf)
  ![image](https://github.com/andrysheq/NeoBank/assets/141515497/0599348e-5c22-4f2a-9f40-77d29d7a59d3)
  http://localhost:8002/swagger-ui/index.html#/
  ![image](https://github.com/andrysheq/NeoBank/assets/141515497/f05b93ed-a4e9-4d6c-8fa6-80e923f808e8)
  http://localhost:8003/swagger-ui/index.html#/
  ![image](https://github.com/andrysheq/NeoBank/assets/141515497/08a5c997-73a2-4832-a868-f9b0da9b0ad6)


Запрос для создания заявки:
http://localhost:8000/application

Request:
  {
    "amount": 100000.00,
    "term": 24,
    "firstName": "Ivan",
    "lastName": "Ivanov",
    "email": "andrey.andreych@bk.ru",
    "middleName": "Ivanovich",
    "birthDate": "2000-01-01",
    "passportSeries": "1234",
    "passportNumber": "567890"
  }

Response:
    [
      {
          "applicationId": 3,
          "totalAmount": 200000.00,
          "requestedAmount": 100000.00,
          "term": 24,
          "monthlyPayment": 4477.26,
          "rate": 7,
          "isInsuranceEnabled": true,
          "isSalaryClient": true
      },
      {
          "applicationId": 3,
          "totalAmount": 200000.00,
          "requestedAmount": 100000.00,
          "term": 24,
          "monthlyPayment": 4522.73,
          "rate": 8,
          "isInsuranceEnabled": true,
          "isSalaryClient": false
      },
      {
          "applicationId": 3,
          "totalAmount": 100000.00,
          "requestedAmount": 100000.00,
          "term": 24,
          "monthlyPayment": 4614.49,
          "rate": 10,
          "isInsuranceEnabled": false,
          "isSalaryClient": true
      },
      {
          "applicationId": 3,
      "totalAmount": 100000.00,
          "requestedAmount": 100000.00,
          "term": 24,
          "monthlyPayment": 4660.78,
          "rate": 11,
          "isInsuranceEnabled": false,
          "isSalaryClient": false
      }
    ]

Запрос для выбора предложения:
http://localhost:8000/application/offer

Request:
  {
    "applicationId": 3,
    "totalAmount": 200000.00,
    "requestedAmount": 100000.00,
    "term": 24,
    "monthlyPayment": 4477.26,
    "rate": 7,
    "isInsuranceEnabled": true,
    "isSalaryClient": true
  }

Response:
  {
    "id": 3,
    "client": {
        "id": 3,
        "amount": 100000.00,
        "term": 24,
        "firstName": "Ivan",
        "lastName": "Ivanov",
        "middleName": "Ivanovich",
        "gender": null,
        "birthDate": "2000-01-01",
        "passportSeries": "1234",
        "passportNumber": "567890",
        "passportIssueDate": null,
        "passportIssueBranch": null,
        "maritalStatus": null,
        "dependentAmount": null,
        "employment": null,
        "account": null,
        "email": "andrey.andreych@bk.ru",
        "isInsuranceEnabled": null,
        "isSalaryClient": null
    },
    "credit": null,
    "status": "PREAPPROVAL",
    "creationDate": "2024-07-11T13:55:08.258988",
    "signDate": null,
    "sesCode": null,
    "loanOffer": {
        "applicationId": 3,
        "totalAmount": 200000.00,
        "requestedAmount": 100000.00,
        "term": 24,
        "monthlyPayment": 4477.26,
        "rate": 7,
        "isInsuranceEnabled": true,
        "isSalaryClient": true
    },
    "statusHistory": [
        {
            "status": "PREAPPROVAL",
            "time": "2024-07-11T13:55:08.257987",
            "changeType": "APPROVED"
        }
    ]
  }

Запрос для расчета кредита:
http://localhost:8000/deal/calculate?applicationId=3

Request: 
  {
    "gender":"MALE",
    "maritalStatus":"MARRIED",
    "dependentAmount":2,
    "passportIssueDate":"2000-11-11",
    "passportIssueBranch":"ОМВД РОССИИ ПО ПЕНЗЕНСКОЙ ОБЛАСТИ",
    "employment":{
        "employmentStatus":"EMPLOYEE",
        "employerInn":"234234234",
        "salary":500000,
        "position":"TOP_MANAGER",
        "workExperienceTotal":123,
        "workExperienceCurrent":12
    },
    "account":"andrew123"
  }

Response:
  {
    "id": 3,
    "client": {
        "id": 3,
        "amount": 100000.00,
        "term": 24,
        "firstName": "Ivan",
        "lastName": "Ivanov",
        "middleName": "Ivanovich",
        "gender": "MALE",
        "birthDate": "2000-01-01",
        "passportSeries": "1234",
        "passportNumber": "567890",
        "passportIssueDate": "2000-11-11",
        "passportIssueBranch": "ОМВД РОССИИ ПО ПЕНЗЕНСКОЙ ОБЛАСТИ",
        "maritalStatus": "MARRIED",
        "dependentAmount": 2,
        "employment": {
            "employmentStatus": "EMPLOYEE",
            "employerInn": "234234234",
            "salary": 500000,
            "position": "TOP_MANAGER",
            "workExperienceTotal": 123,
            "workExperienceCurrent": 12
        },
        "account": "andrew123",
        "email": "andrey.andreych@bk.ru",
        "isInsuranceEnabled": true,
        "isSalaryClient": true
    },
    "credit": {
        "id": 2,
        "amount": 200000.00,
        "term": 24,
        "monthlyPayment": 4387.14,
        "rate": 5,
        "psk": 105.300,
        "isInsuranceEnabled": true,
        "isSalaryClient": true,
        "creditStatus": "CALCULATED",
        "paymentSchedule": [
            {
                "number": 1,
                "date": "2024-08-11",
                "totalPayment": 4387.14,
                "interestPayment": 416.67,
                "debtPayment": 3970.47,
                "remainingDebt": 96029.53
            },
            {
                "number": 2,
                "date": "2024-09-11",
                "totalPayment": 4387.14,
                "interestPayment": 400.12,
                "debtPayment": 3987.02,
                "remainingDebt": 92042.51
            },
            {
                "number": 3,
                "date": "2024-10-11",
                "totalPayment": 4387.14,
                "interestPayment": 383.51,
                "debtPayment": 4003.63,
                "remainingDebt": 88038.88
            },
            {
                "number": 4,
                "date": "2024-11-11",
                "totalPayment": 4387.14,
                "interestPayment": 366.83,
                "debtPayment": 4020.31,
                "remainingDebt": 84018.57
            },
            {
                "number": 5,
                "date": "2024-12-11",
                "totalPayment": 4387.14,
                "interestPayment": 350.08,
                "debtPayment": 4037.06,
                "remainingDebt": 79981.51
            },
            {
                "number": 6,
                "date": "2025-01-11",
                "totalPayment": 4387.14,
                "interestPayment": 333.26,
                "debtPayment": 4053.88,
                "remainingDebt": 75927.62
            },
            {
                "number": 7,
                "date": "2025-02-11",
                "totalPayment": 4387.14,
                "interestPayment": 316.37,
                "debtPayment": 4070.77,
                "remainingDebt": 71856.85
            },
            {
                "number": 8,
                "date": "2025-03-11",
                "totalPayment": 4387.14,
                "interestPayment": 299.40,
                "debtPayment": 4087.74,
                "remainingDebt": 67769.11
            },
            {
                "number": 9,
                "date": "2025-04-11",
                "totalPayment": 4387.14,
                "interestPayment": 282.37,
                "debtPayment": 4104.77,
                "remainingDebt": 63664.34
            },
            {
                "number": 10,
                "date": "2025-05-11",
                "totalPayment": 4387.14,
                "interestPayment": 265.27,
                "debtPayment": 4121.87,
                "remainingDebt": 59542.47
            },
            {
                "number": 11,
                "date": "2025-06-11",
                "totalPayment": 4387.14,
                "interestPayment": 248.09,
                "debtPayment": 4139.05,
                "remainingDebt": 55403.42
            },
            {
                "number": 12,
                "date": "2025-07-11",
                "totalPayment": 4387.14,
                "interestPayment": 230.85,
                "debtPayment": 4156.29,
                "remainingDebt": 51247.13
            },
            {
                "number": 13,
                "date": "2025-08-11",
                "totalPayment": 4387.14,
                "interestPayment": 213.53,
                "debtPayment": 4173.61,
                "remainingDebt": 47073.52
            },
            {
                "number": 14,
                "date": "2025-09-11",
                "totalPayment": 4387.14,
                "interestPayment": 196.14,
                "debtPayment": 4191.00,
                "remainingDebt": 42882.52
            },
            {
                "number": 15,
                "date": "2025-10-11",
                "totalPayment": 4387.14,
                "interestPayment": 178.68,
                "debtPayment": 4208.46,
                "remainingDebt": 38674.06
            },
            {
                "number": 16,
                "date": "2025-11-11",
                "totalPayment": 4387.14,
                "interestPayment": 161.14,
                "debtPayment": 4226.00,
                "remainingDebt": 34448.06
            },
            {
                "number": 17,
                "date": "2025-12-11",
                "totalPayment": 4387.14,
                "interestPayment": 143.53,
                "debtPayment": 4243.61,
                "remainingDebt": 30204.45
            },
            {
                "number": 18,
                "date": "2026-01-11",
                "totalPayment": 4387.14,
                "interestPayment": 125.85,
                "debtPayment": 4261.29,
                "remainingDebt": 25943.17
            },
            {
                "number": 19,
                "date": "2026-02-11",
                "totalPayment": 4387.14,
                "interestPayment": 108.10,
                "debtPayment": 4279.04,
                "remainingDebt": 21664.12
            },
            {
                "number": 20,
                "date": "2026-03-11",
                "totalPayment": 4387.14,
                "interestPayment": 90.27,
                "debtPayment": 4296.87,
                "remainingDebt": 17367.25
            },
            {
                "number": 21,
                "date": "2026-04-11",
                "totalPayment": 4387.14,
                "interestPayment": 72.36,
                "debtPayment": 4314.78,
                "remainingDebt": 13052.47
            },
            {
                "number": 22,
                "date": "2026-05-11",
                "totalPayment": 4387.14,
                "interestPayment": 54.39,
                "debtPayment": 4332.75,
                "remainingDebt": 8719.72
            },
            {
                "number": 23,
                "date": "2026-06-11",
                "totalPayment": 4387.14,
                "interestPayment": 36.33,
                "debtPayment": 4350.81,
                "remainingDebt": 4368.91
            },
            {
                "number": 24,
                "date": "2026-07-11",
                "totalPayment": 4387.11,
                "interestPayment": 18.20,
                "debtPayment": 4368.91,
                "remainingDebt": 0.00
            }
        ]
    },
    "status": "CC_APPROVED",
    "creationDate": "2024-07-11T13:55:08.258988",
    "signDate": null,
    "sesCode": null,
    "loanOffer": {
        "applicationId": 3,
        "totalAmount": 200000.00,
        "requestedAmount": 100000.00,
        "term": 24,
        "monthlyPayment": 4477.26,
        "rate": 7.00,
        "isInsuranceEnabled": true,
        "isSalaryClient": true
    },
    "statusHistory": [
        {
            "status": "PREAPPROVAL",
            "time": "2024-07-11T13:55:08.257987",
            "changeType": "APPROVED"
        },
        {
            "status": "CC_APPROVED",
            "time": "2024-07-11T13:55:29.0949435",
            "changeType": "APPROVED"
        }
    ]
  }
  
Запрос для подготовки документов и отправки на подписание:
http://localhost:8000/deal/document/3/sign

Response:
  Документы отправлены на почту клиента

Запрос для подготовки документов и отправки на подписание:
http://localhost:8000/deal/document/3/code?sesCode=3327366373

Response:
  {
    "id": 3,
    "client": {
        "id": 3,
        "amount": 100000.00,
        "term": 24,
        "firstName": "Ivan",
        "lastName": "Ivanov",
        "middleName": "Ivanovich",
        "gender": "MALE",
        "birthDate": "2000-01-01",
        "passportSeries": "1234",
        "passportNumber": "567890",
        "passportIssueDate": "2000-11-11",
        "passportIssueBranch": "ОМВД РОССИИ ПО ПЕНЗЕНСКОЙ ОБЛАСТИ",
        "maritalStatus": "MARRIED",
        "dependentAmount": 2,
        "employment": {
            "employmentStatus": "EMPLOYEE",
            "employerInn": "234234234",
            "salary": 500000.00,
            "position": "TOP_MANAGER",
            "workExperienceTotal": 123,
            "workExperienceCurrent": 12
        },
        "account": "andrew123",
        "email": "andrey.andreych@bk.ru",
        "isInsuranceEnabled": true,
        "isSalaryClient": true
    },
    "credit": {
        "id": 2,
        "amount": 200000.00,
        "term": 24,
        "monthlyPayment": 4387.14,
        "rate": 5.00,
        "psk": 105.30,
        "isInsuranceEnabled": true,
        "isSalaryClient": true,
        "creditStatus": "ISSUED",
        "paymentSchedule": [
            {
                "number": 1,
                "date": "2024-08-11",
                "totalPayment": 4387.14,
                "interestPayment": 416.67,
                "debtPayment": 3970.47,
                "remainingDebt": 96029.53
            },
            {
                "number": 2,
                "date": "2024-09-11",
                "totalPayment": 4387.14,
                "interestPayment": 400.12,
                "debtPayment": 3987.02,
                "remainingDebt": 92042.51
            },
            {
                "number": 3,
                "date": "2024-10-11",
                "totalPayment": 4387.14,
                "interestPayment": 383.51,
                "debtPayment": 4003.63,
                "remainingDebt": 88038.88
            },
            {
                "number": 4,
                "date": "2024-11-11",
                "totalPayment": 4387.14,
                "interestPayment": 366.83,
                "debtPayment": 4020.31,
                "remainingDebt": 84018.57
            },
            {
                "number": 5,
                "date": "2024-12-11",
                "totalPayment": 4387.14,
                "interestPayment": 350.08,
                "debtPayment": 4037.06,
                "remainingDebt": 79981.51
            },
            {
                "number": 6,
                "date": "2025-01-11",
                "totalPayment": 4387.14,
                "interestPayment": 333.26,
                "debtPayment": 4053.88,
                "remainingDebt": 75927.62
            },
            {
                "number": 7,
                "date": "2025-02-11",
                "totalPayment": 4387.14,
                "interestPayment": 316.37,
                "debtPayment": 4070.77,
                "remainingDebt": 71856.85
            },
            {
                "number": 8,
                "date": "2025-03-11",
                "totalPayment": 4387.14,
                "interestPayment": 299.40,
                "debtPayment": 4087.74,
                "remainingDebt": 67769.11
            },
            {
                "number": 9,
                "date": "2025-04-11",
                "totalPayment": 4387.14,
                "interestPayment": 282.37,
                "debtPayment": 4104.77,
                "remainingDebt": 63664.34
            },
            {
                "number": 10,
                "date": "2025-05-11",
                "totalPayment": 4387.14,
                "interestPayment": 265.27,
                "debtPayment": 4121.87,
                "remainingDebt": 59542.47
            },
            {
                "number": 11,
                "date": "2025-06-11",
                "totalPayment": 4387.14,
                "interestPayment": 248.09,
                "debtPayment": 4139.05,
                "remainingDebt": 55403.42
            },
            {
                "number": 12,
                "date": "2025-07-11",
                "totalPayment": 4387.14,
                "interestPayment": 230.85,
                "debtPayment": 4156.29,
                "remainingDebt": 51247.13
            },
            {
                "number": 13,
                "date": "2025-08-11",
                "totalPayment": 4387.14,
                "interestPayment": 213.53,
                "debtPayment": 4173.61,
                "remainingDebt": 47073.52
            },
            {
                "number": 14,
                "date": "2025-09-11",
                "totalPayment": 4387.14,
                "interestPayment": 196.14,
                "debtPayment": 4191.00,
                "remainingDebt": 42882.52
            },
            {
                "number": 15,
                "date": "2025-10-11",
                "totalPayment": 4387.14,
                "interestPayment": 178.68,
                "debtPayment": 4208.46,
                "remainingDebt": 38674.06
            },
            {
                "number": 16,
                "date": "2025-11-11",
                "totalPayment": 4387.14,
                "interestPayment": 161.14,
                "debtPayment": 4226.00,
                "remainingDebt": 34448.06
            },
            {
                "number": 17,
                "date": "2025-12-11",
                "totalPayment": 4387.14,
                "interestPayment": 143.53,
                "debtPayment": 4243.61,
                "remainingDebt": 30204.45
            },
            {
                "number": 18,
                "date": "2026-01-11",
                "totalPayment": 4387.14,
                "interestPayment": 125.85,
                "debtPayment": 4261.29,
                "remainingDebt": 25943.17
            },
            {
                "number": 19,
                "date": "2026-02-11",
                "totalPayment": 4387.14,
                "interestPayment": 108.10,
                "debtPayment": 4279.04,
                "remainingDebt": 21664.12
            },
            {
                "number": 20,
                "date": "2026-03-11",
                "totalPayment": 4387.14,
                "interestPayment": 90.27,
                "debtPayment": 4296.87,
                "remainingDebt": 17367.25
            },
            {
                "number": 21,
                "date": "2026-04-11",
                "totalPayment": 4387.14,
                "interestPayment": 72.36,
                "debtPayment": 4314.78,
                "remainingDebt": 13052.47
            },
            {
                "number": 22,
                "date": "2026-05-11",
                "totalPayment": 4387.14,
                "interestPayment": 54.39,
                "debtPayment": 4332.75,
                "remainingDebt": 8719.72
            },
            {
                "number": 23,
                "date": "2026-06-11",
                "totalPayment": 4387.14,
                "interestPayment": 36.33,
                "debtPayment": 4350.81,
                "remainingDebt": 4368.91
            },
            {
                "number": 24,
                "date": "2026-07-11",
                "totalPayment": 4387.11,
                "interestPayment": 18.20,
                "debtPayment": 4368.91,
                "remainingDebt": 0.00
            }
        ]
    },
    "status": "CREDIT_ISSUED",
    "creationDate": "2024-07-11T13:55:08.258988",
    "signDate": "2024-07-11T13:56:15.2776989",
    "sesCode": "3327366373",
    "loanOffer": {
        "applicationId": 3,
        "totalAmount": 200000.00,
        "requestedAmount": 100000.00,
        "term": 24,
        "monthlyPayment": 4477.26,
        "rate": 7.00,
        "isInsuranceEnabled": true,
        "isSalaryClient": true
    },
    "statusHistory": [
        {
            "status": "PREAPPROVAL",
            "time": "2024-07-11T13:55:08.257987",
            "changeType": "APPROVED"
        },
        {
            "status": "CC_APPROVED",
            "time": "2024-07-11T13:55:29.094944",
            "changeType": "APPROVED"
        },
        {
            "status": "DOCUMENT_CREATED",
            "time": "2024-07-11T13:55:44.003932",
            "changeType": "APPROVED"
        },
        {
            "status": "DOCUMENT_SIGNED",
            "time": "2024-07-11T13:56:15.2766988",
            "changeType": "APPROVED"
        },
        {
            "status": "CREDIT_ISSUED",
            "time": "2024-07-11T13:56:15.2776989",
            "changeType": "APPROVED"
        }
    ]
  }

Тестирование в Postman:
    Создание заявки:
      ![image](https://github.com/andrysheq/NeoBank/assets/141515497/2de99660-d068-4ae3-a7d2-11d940890d4d)
    Подтверждение заявки: 
      ![image](https://github.com/andrysheq/NeoBank/assets/141515497/405e882f-888a-4ed9-bbc5-d99ecee1606b)
    Завершение регистрации и расчет кредита: 
      ![image](https://github.com/andrysheq/NeoBank/assets/141515497/1e360ca3-9f2d-436f-8263-7087ae1d4b12)
    Запрос на формирование и отправку документов: 
      ![image](https://github.com/andrysheq/NeoBank/assets/141515497/a1ed3888-a316-4212-b148-d46357676365)
    Ввод полученного кода и подтверждение получения кредита: 
      ![image](https://github.com/andrysheq/NeoBank/assets/141515497/5c439593-3c1f-4d75-bb20-f3dc0dc42168)
    ------------------------------------------------------------------------------------------------------

Ответы на некоторые ошибочные запросы:
      Несуществующий номер заявки: 
        ![image](https://github.com/andrysheq/NeoBank/assets/141515497/77447e62-74cc-490d-bb4c-567be6138465)
      Несовпадающий код подверждения:
        ![image](https://github.com/andrysheq/NeoBank/assets/141515497/5939289a-621e-4824-beec-10223c7b6179)
      Некорректная сумма кредита во время прескоринга: 
        ![image](https://github.com/andrysheq/NeoBank/assets/141515497/9fc71580-9586-45fe-9274-a638b2f9756e)
      Отказ скоринговой системы в связи с малым стажем:
        ![image](https://github.com/andrysheq/NeoBank/assets/141515497/fc2cc02c-bedb-4021-b067-0c3e71ac0989)

      







