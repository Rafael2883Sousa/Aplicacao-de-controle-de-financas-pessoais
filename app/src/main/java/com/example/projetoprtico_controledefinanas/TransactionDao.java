package com.example.projetoprtico_controledefinanas;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

    @Dao
    public interface TransactionDao {

        @Insert
        void insert(Transaction transaction);

        @Delete
        void delete(Transaction transaction);

        @Query("SELECT * FROM transactions ORDER BY date DESC")
        LiveData<List<Transaction>> getAllTransactions();

        @Query("SELECT * FROM transactions WHERE id = :transactionId LIMIT 1")
        LiveData<Transaction> getTransactionById(int transactionId);

    }

