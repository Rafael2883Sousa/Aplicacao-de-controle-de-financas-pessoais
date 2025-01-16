package com.example.projetoprtico_controledefinanas;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransactionRepository {

    private TransactionDao transactionDao; // Interface para acessar as transações no banco de dados
    private LiveData<List<Transaction>> allTransactions; // Lista de todas as transações
    private ExecutorService executorService; // Executor para as operações


    public TransactionRepository(Application application) {
        // Obtém a instância do banco de dados e do DAO
        TransactionDatabase database = TransactionDatabase.getInstance(application);
        transactionDao = database.transactionDao();
        allTransactions = transactionDao.getAllTransactions(); // Obtém a lista de todas as transações
        executorService = Executors.newFixedThreadPool(2);
    }

    public void insert(Transaction transaction) {
        // Executa a inserção em uma thread em segundo plano
        executorService.execute(() -> transactionDao.insert(transaction));
    }

    public void delete(Transaction transaction) {
        // Executa a exclusão em uma thread em segundo plano
        executorService.execute(() -> transactionDao.delete(transaction));
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        return allTransactions; // Retorna a lista de todas as transações
    }

    public LiveData<Transaction> getTransactionById(int transactionId) {
        return transactionDao.getTransactionById(transactionId); // Retorna a transação com o ID especificado
    }

}
