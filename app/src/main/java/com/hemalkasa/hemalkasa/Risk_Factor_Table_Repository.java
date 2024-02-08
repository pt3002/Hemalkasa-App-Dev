package com.hemalkasa.hemalkasa;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

public class Risk_Factor_Table_Repository {
    public static final String TAG = "pratik";
    private Risk_Factor_Table_DAO riskFactorTableDao;


    public Risk_Factor_Table_Repository(Application application) {
        riskFactorTableDao = Database.getInstance(application).riskFactorTableDao();
    }

    public void insertRiskFactor(Risk_Factor_Table riskFactorTable){
        new InsertAsyncRiskFactor(riskFactorTableDao).execute(riskFactorTable);
    }

    public void updateRiskFactor(Risk_Factor_Table riskFactorTable){
        new UpdateAsyncRiskFactor(riskFactorTableDao).execute(riskFactorTable);
    }

    public void deleteRiskFactor(Risk_Factor_Table riskFactorTable){
        new DeleteAsyncRiskFactor(riskFactorTableDao).execute(riskFactorTable);
    }

    public  void getAllRiskFactors(RiskFactorAdaptor riskFactorAdaptor){
        new GetAllRiskFactorsAsync(riskFactorTableDao, riskFactorAdaptor).execute();
    }


    public  void getActiveRiskFactors(RiskFactorAdaptor riskFactorAdaptor){
        new GetActiveRiskFactors(riskFactorTableDao, riskFactorAdaptor).execute();
    }

    private static class InsertAsyncRiskFactor extends AsyncTask<Risk_Factor_Table,Void,Void>{
        private Risk_Factor_Table_DAO riskFactorTableDao;

        public InsertAsyncRiskFactor(Risk_Factor_Table_DAO riskFactorTableDao) {
            this.riskFactorTableDao = riskFactorTableDao;
        }

        @Override
        protected Void doInBackground(Risk_Factor_Table... risk_factor_tables) {
            riskFactorTableDao.insertRiskFactor(risk_factor_tables[0]);
            return null;
        }
    }

    private static class UpdateAsyncRiskFactor extends AsyncTask<Risk_Factor_Table,Void,Void>{
        private Risk_Factor_Table_DAO riskFactorTableDao;

        public UpdateAsyncRiskFactor(Risk_Factor_Table_DAO riskFactorTableDao) {
            this.riskFactorTableDao = riskFactorTableDao;
        }

        @Override
        protected Void doInBackground(Risk_Factor_Table... risk_factor_tables) {
            riskFactorTableDao.updateRiskFactor(risk_factor_tables[0]);
            return null;
        }
    }

    private static class DeleteAsyncRiskFactor extends AsyncTask<Risk_Factor_Table,Void,Void>{
        private Risk_Factor_Table_DAO riskFactorTableDao;

        public DeleteAsyncRiskFactor(Risk_Factor_Table_DAO riskFactorTableDao) {
            this.riskFactorTableDao = riskFactorTableDao;
        }

        @Override
        protected Void doInBackground(Risk_Factor_Table... risk_factor_tables) {
            riskFactorTableDao.deleteRiskFactor(risk_factor_tables[0]);
            return null;
        }
    }

    private static class GetAllRiskFactorsAsync extends AsyncTask<Void,Void, List<Risk_Factor_Table>>{
        private Risk_Factor_Table_DAO riskFactorTableDao;
        RiskFactorAdaptor riskFactorAdaptor;

        public GetAllRiskFactorsAsync(Risk_Factor_Table_DAO riskFactorTableDao, RiskFactorAdaptor riskFactorAdaptor) {
            this.riskFactorTableDao = riskFactorTableDao;
            this.riskFactorAdaptor = riskFactorAdaptor;
        }

        @Override
        protected List<Risk_Factor_Table> doInBackground(Void... voids) {
            return riskFactorTableDao.getAllRiskFactors();
        }

        @Override
        protected void onPostExecute(List<Risk_Factor_Table> risk_factor_tables) {
            super.onPostExecute(risk_factor_tables);
//            riskFactorAdaptor.setRiskFactorList(risk_factor_tables);
        }
    }

    private static class GetActiveRiskFactors extends AsyncTask<Void,Void, List<Risk_Factor_Table>>{
        private Risk_Factor_Table_DAO riskFactorTableDao;
        RiskFactorAdaptor riskFactorAdaptor;

        public GetActiveRiskFactors(Risk_Factor_Table_DAO riskFactorTableDao, RiskFactorAdaptor riskFactorAdaptor) {
            this.riskFactorTableDao = riskFactorTableDao;
            this.riskFactorAdaptor = riskFactorAdaptor;
        }

        @Override
        protected List<Risk_Factor_Table> doInBackground(Void... voids) {
            return riskFactorTableDao.getActiveRiskFactors(true);
        }

        @Override
        protected void onPostExecute(List<Risk_Factor_Table> risk_factor_tables) {
            super.onPostExecute(risk_factor_tables);
//            riskFactorAdaptor.setRiskFactorList(risk_factor_tables);
        }
    }


}



