package co.edu.eafit.equations.inputs.equationssystems;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import org.ejml.simple.SimpleMatrix;

import co.edu.eafit.equations.R;
import co.edu.eafit.equations.Tabs;
import co.edu.eafit.equations.Methods;
import co.edu.eafit.equations.tables.equiationssystems.TableGaussianEliminationWithPartialPivoting;

public class InputGEWithPartialPivoting extends Fragment {
    TableLayout matrixinput;
    TableLayout vectorbinput;
    int size;
    public static InputGEWithPartialPivoting newInstance() {
        InputGEWithPartialPivoting fragment = new InputGEWithPartialPivoting();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab_input_ge_with_partial_pivoting, container, false);
        final EditText inputMatrixSize = (EditText)rootView.findViewById(R.id.input_matrix_size);
        Button btnMatrixSize = (Button)rootView.findViewById(R.id.btn_matrix_size);
        matrixinput = (TableLayout)rootView.findViewById(R.id.MatrixA);
        vectorbinput = (TableLayout)rootView.findViewById(R.id.VectorB);
        final Button btnCalculate = (Button)rootView.findViewById(R.id.btn_calculate);
        btnMatrixSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matrixinput.removeAllViews();
                vectorbinput.removeAllViews();
                String sSize = inputMatrixSize.getText().toString();

                if(sSize.isEmpty()||sSize==null||sSize.equals("")){
                    size=4;
                }else{
                    size = Integer.parseInt(sSize);
                }
                for (int i=0;i<size;i++){
                    TableRow row = new TableRow(rootView.getContext());
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row.setLayoutParams(lp);
                    for(int j=0;j<size;j++){
                        EditText input = new EditText(rootView.getContext());
                        row.addView(input);
                        input.getLayoutParams().width=100;
                        input.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_NUMBER_FLAG_SIGNED);
                    }
                    matrixinput.addView(row);
                }
                TableRow vectorb = new TableRow(rootView.getContext());
                TableRow.LayoutParams vectorblp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                vectorb.setLayoutParams(vectorblp);
                for(int i=0;i<size;i++){
                    EditText input = new EditText(rootView.getContext());
                    vectorb.addView(input);
                    input.getLayoutParams().width=100;
                    input.setInputType(InputType.TYPE_CLASS_NUMBER
                            | InputType.TYPE_NUMBER_FLAG_DECIMAL
                            | InputType.TYPE_NUMBER_FLAG_SIGNED);
                }
                vectorbinput.addView(vectorb);
                btnCalculate.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT)
                );
                btnCalculate.setVisibility(View.VISIBLE);
            }
        });
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleMatrix matrix = new SimpleMatrix(matrixinput.getChildCount(),matrixinput.getChildCount());
                SimpleMatrix vectorb = new SimpleMatrix(matrixinput.getChildCount(),1);
                for(int i = 0; i < matrixinput.getChildCount();i++){
                    TableRow row = (TableRow)matrixinput.getChildAt(i);
                    for(int j = 0; j < row.getChildCount(); j++){
                        EditText input = (EditText)row.getChildAt(j);
                        matrix.set(i,j,Double.parseDouble(input.getText().toString()));
                    }
                }
                TableRow rowx = (TableRow)vectorbinput.getChildAt(0);
                for(int i = 0; i < rowx.getChildCount() ; i++){
                    EditText input = (EditText)rowx.getChildAt(i);
                    vectorb.set(i,0,Double.parseDouble(input.getText().toString()));
                }
                Tabs activity = (Tabs)getActivity();
                TableGaussianEliminationWithPartialPivoting tabgauelm = (TableGaussianEliminationWithPartialPivoting)activity.getFragmentTable();
                //tabgauelm.getText().setText(matrix.toString()+"\n"+vectorb.toString());
                double elementoMayor = Math.abs(matrix.get(size - 1, size - 1));
                if(elementoMayor != 0) {
                    SimpleMatrix aux = Methods.eliminacionGaussianaConPivoteoParcial(matrix, vectorb);
                    SimpleMatrix res = Methods.sustitucionRegresiva(aux);

                    String xx = "";
                    for (int k = 0; k < size; k++)
                        xx += "X" + (k + 1) + " = " + res.get(k) + "\n";

                    tabgauelm.getText().setText("MATRIZ A \n" + matrix.toString() + "\n VECTOR B \n" +
                            vectorb.toString() + "\n PARTIAL PIVOTING \n " + aux.toString() + "\n ANSWERS \n" + xx);
                }else{
                       tabgauelm.getText().setText("El sistema no tiene solucion");
                }

            }
        });
        return rootView;
    }
}
