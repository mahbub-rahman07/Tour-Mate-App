package com.mahbub.tourmate.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.mahbub.tourmate.R;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrencyConvertFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private EditText fromMoneyEdit;
    private EditText toMoneyEdit;
    private Spinner toMoneySpinner,fromMoneySpinner;
    private Button convertMoneyBtn;


    private Map<String,Double> currency;

    public CurrencyConvertFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_currency_convert, container, false);

        fromMoneyEdit = view.findViewById(R.id.from_money_edit);
        toMoneyEdit = view.findViewById(R.id.to_money_edit);
        fromMoneySpinner = view.findViewById(R.id.from_money_spinner);
        toMoneySpinner = view.findViewById(R.id.to_money_spinner);
        convertMoneyBtn = view.findViewById(R.id.convert_money_btn);
        currency = new HashMap<>();
        currency.put("US Dolar", 0.012);
        currency.put("EURO", 0.0098);
        currency.put("Algerian Dinar", 1.37);
        currency.put("Indian Rupee", 0.77);
        currency.put("Indonesian Rupiah", 163.36);
        currency.put("Malaysian Ringgit", 0.047);
        currency.put("Bangladeshi Taka", 1.0);

        convertMoneyBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        DecimalFormat df = new DecimalFormat("#.###");
        String fromMoneyString = fromMoneyEdit.getText().toString();
        toMoneyEdit.setText("0.0");
        String fmSp = fromMoneySpinner.getSelectedItem().toString();
        String toSp = toMoneySpinner.getSelectedItem().toString();

        if (!TextUtils.isEmpty(fromMoneyString)) {
            double convertFromMoney = currency.get(fmSp);
            double convertToMoney = currency.get(toSp);
            double fromMoney = Double.parseDouble(fromMoneyString);

            double moneyInTk = ( fromMoney / convertFromMoney );
            double resultMoeny =  Double.valueOf(df.format(( moneyInTk * convertToMoney)));
            toMoneyEdit.setText(resultMoeny +"");

            Log.d("CURRENCY", "onClick: from "+convertFromMoney +" to "+convertToMoney +" inTk "+moneyInTk +" result "+ resultMoeny);


        }
    }
}
