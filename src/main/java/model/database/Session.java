package model.database;

import com.mongodb.ClientSessionOptions;
import com.mongodb.ServerAddress;
import com.mongodb.TransactionOptions;
import com.mongodb.client.ClientSession;
import com.mongodb.client.TransactionBody;
import com.mongodb.session.ServerSession;
import org.bson.BsonDocument;
import org.bson.BsonTimestamp;

public class Session {

    ClientSession session = new ClientSession() {
        @Override
        public ServerAddress getPinnedServerAddress() {
            return null;
        }

        @Override
        public void setPinnedServerAddress(ServerAddress serverAddress) {

        }

        @Override
        public boolean hasActiveTransaction() {
            return false;
        }

        @Override
        public boolean notifyMessageSent() {
            return false;
        }

        @Override
        public TransactionOptions getTransactionOptions() {
            return null;
        }

        @Override
        public void startTransaction() {

        }

        @Override
        public void startTransaction(TransactionOptions transactionOptions) {

        }

        @Override
        public void commitTransaction() {

        }

        @Override
        public void abortTransaction() {

        }

        @Override
        public <T> T withTransaction(TransactionBody<T> transactionBody) {
            return null;
        }

        @Override
        public <T> T withTransaction(TransactionBody<T> transactionBody, TransactionOptions transactionOptions) {
            return null;
        }

        @Override
        public BsonDocument getRecoveryToken() {
            return null;
        }

        @Override
        public void setRecoveryToken(BsonDocument bsonDocument) {

        }

        @Override
        public ClientSessionOptions getOptions() {
            return null;
        }

        @Override
        public boolean isCausallyConsistent() {
            return true;
        }

        @Override
        public Object getOriginator() {
            return null;
        }

        @Override
        public ServerSession getServerSession() {
            return null;
        }

        @Override
        public BsonTimestamp getOperationTime() {
            return null;
        }

        @Override
        public void advanceOperationTime(BsonTimestamp bsonTimestamp) {

        }

        @Override
        public void advanceClusterTime(BsonDocument bsonDocument) {

        }

        @Override
        public BsonDocument getClusterTime() {
            return null;
        }

        @Override
        public void close() {

        }
    };

}
