package com.example.projetoprtico_controledefinanas;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Transaction.class}, version = 1) // Define a classe Transaction como uma entidade do banco de dados
@TypeConverters({Converters.class}) // Converte tipos personalizados para serem armazenados no banco de dados
public abstract class TransactionDatabase extends RoomDatabase {

    private static TransactionDatabase instance; // Instância única do banco de dados

    // Define o DAO para acessar os dados de transações
    public abstract TransactionDao transactionDao();


    public static synchronized TransactionDatabase getInstance(Context context) {
        if (instance == null) {
            // Cria a instância do banco de dados se ainda não existir
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            TransactionDatabase.class, "transaction_database")
                    .fallbackToDestructiveMigration() // Quando a versão do banco é alterada, a migração pode ser feita de forma destrutiva
                    .build();
        }
        return instance; // Retorna a instância do banco de dados
    }
}
