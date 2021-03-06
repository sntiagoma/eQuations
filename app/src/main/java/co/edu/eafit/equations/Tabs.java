package co.edu.eafit.equations;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import co.edu.eafit.equations.helps.Help;
import co.edu.eafit.equations.inputs.equationssystems.InputCholesky;
import co.edu.eafit.equations.inputs.equationssystems.InputCrout;
import co.edu.eafit.equations.inputs.equationssystems.InputDoolittle;
import co.edu.eafit.equations.inputs.equationssystems.InputGEWithTotalPivoting;
import co.edu.eafit.equations.inputs.equationssystems.InputGaussSeidel;
import co.edu.eafit.equations.inputs.equationssystems.InputGaussianElimination;
import co.edu.eafit.equations.inputs.equationssystems.InputGEWithPartialPivoting;
import co.edu.eafit.equations.inputs.equationssystems.InputGEWithStaggeredPivoting;
import co.edu.eafit.equations.inputs.equationssystems.InputJacobi;
import co.edu.eafit.equations.inputs.equationssystems.Load;
import co.edu.eafit.equations.inputs.interpolation.InputCubicSpline;
import co.edu.eafit.equations.inputs.interpolation.InputLagrangePolynomial;
import co.edu.eafit.equations.inputs.interpolation.InputLinearSpline;
import co.edu.eafit.equations.inputs.interpolation.InputNeville;
import co.edu.eafit.equations.inputs.interpolation.InputNewtonPolynomial;
import co.edu.eafit.equations.inputs.singlevariable.InputBisection;
import co.edu.eafit.equations.inputs.singlevariable.InputFalsePosition;
import co.edu.eafit.equations.inputs.singlevariable.InputFixedPoint;
import co.edu.eafit.equations.inputs.singlevariable.InputIncrementalSearches;
import co.edu.eafit.equations.inputs.singlevariable.InputMultipleRoots;
import co.edu.eafit.equations.inputs.singlevariable.InputNewton;
import co.edu.eafit.equations.inputs.singlevariable.InputSecant;
import co.edu.eafit.equations.tables.equiationssystems.TableCholesky;
import co.edu.eafit.equations.tables.equiationssystems.TableCrout;
import co.edu.eafit.equations.tables.equiationssystems.TableDoolittle;
import co.edu.eafit.equations.tables.equiationssystems.TableGEWithTotalPivoting;
import co.edu.eafit.equations.tables.equiationssystems.TableGaussSeidel;
import co.edu.eafit.equations.tables.equiationssystems.TableGaussianElimination;
import co.edu.eafit.equations.tables.equiationssystems.TableGaussianEliminationWithPartialPivoting;
import co.edu.eafit.equations.tables.equiationssystems.TableGaussianEliminationWithStaggeredPivoting;
import co.edu.eafit.equations.tables.interpolation.TableCubicSpline;
import co.edu.eafit.equations.tables.interpolation.TableLagrangePolynomial;
import co.edu.eafit.equations.tables.interpolation.TableLinearSpline;
import co.edu.eafit.equations.tables.interpolation.TableNeville;
import co.edu.eafit.equations.tables.interpolation.TableNewtonPolynomial;
import co.edu.eafit.equations.tables.singlevariable.TableBisection;
import co.edu.eafit.equations.tables.singlevariable.TableFalsePosition;
import co.edu.eafit.equations.tables.singlevariable.TableFixedPoint;
import co.edu.eafit.equations.tables.singlevariable.TableIncrementalSearches;
import co.edu.eafit.equations.tables.singlevariable.TableJacobi;
import co.edu.eafit.equations.tables.singlevariable.TableMultipleRoots;
import co.edu.eafit.equations.tables.singlevariable.TableNewton;
import co.edu.eafit.equations.tables.singlevariable.TableSecant;

public class Tabs extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Fragment fragmentInput;
    private Fragment fragmentTable;
    private Fragment fragmentHelp;
    public  Fragment getFragmentInput(){return this.fragmentInput;}
    public  Fragment getFragmentTable(){return this.fragmentTable;}
    public  Fragment getFragmentHelp(){return this.fragmentHelp;}
    public void setFragmentInput(Fragment fragment){this.fragmentInput = fragment;}
    public void setFragmentTable(Fragment fragment){this.fragmentTable = fragment;}
    public void setFragmentHelp(Fragment fragment){this.fragmentHelp = fragment;}
    private Tabla tabla;
    public Tabla getTabla(){
        return this.tabla;
    }
    public void setTabla(Tabla tabla){
        this.tabla = tabla;
    }
    public String nameToolbar;
    public Tabs returnthis(){return this;}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        nameToolbar = getIntent().getExtras().getString("type");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(nameToolbar);
        setTabla(new Tabla());
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences;
                switch (nameToolbar){
                    case "Incremental Searches":
                    case "Bisection":
                    case "False Position":
                    case "Fixed Point":
                    case "Newton":
                    case "Secant":
                    case "Multiple Roots":
                        preferences = getSharedPreferences("SingleVariable",
                                Context.MODE_PRIVATE);
                        EditText inpt_fx = (EditText)findViewById(R.id.inpt_fx);
                        EditText inpt_delta = (EditText)findViewById(R.id.inpt_delta);
                        EditText inpt_xinit = (EditText)findViewById(R.id.inpt_xinit);
                        EditText inpt_iter = (EditText)findViewById(R.id.inpt_iter);
                        EditText inpt_tol = (EditText)findViewById(R.id.inpt_tol);
                        EditText inpt_xfin = (EditText)findViewById(R.id.inpt_xfin);
                        EditText inpt_gx = (EditText)findViewById(R.id.inpt_gx);
                        EditText inpt_fpx = (EditText)findViewById(R.id.inpt_fpx);
                        EditText inpt_fppx = (EditText)findViewById(R.id.inpt_fppx);
                        try{
                            inpt_fx.setText(preferences.getString("fx",""));
                        }catch (NullPointerException nullp){}
                        try{
                            inpt_delta.setText(preferences.getString("delta",""));
                        }catch (NullPointerException nullp){}
                        try{
                            inpt_xinit.setText(preferences.getString("xinit",""));
                        }catch (NullPointerException nullp){}
                        try{
                            inpt_iter.setText(preferences.getString("iter",""));
                        }catch (NullPointerException nullp){}
                        try{
                            inpt_tol.setText(preferences.getString("tol",""));
                        }catch (NullPointerException nullp){}
                        try{
                            inpt_xfin.setText(preferences.getString("xfin",""));
                        }catch (NullPointerException nullp){}
                        try{
                            inpt_gx.setText(preferences.getString("gx",""));
                        }catch (NullPointerException nullp){}
                        try{
                            inpt_fpx.setText(preferences.getString("fpx",""));
                        }catch (NullPointerException nullp){}
                        try{
                            inpt_fppx.setText(preferences.getString("fpx",""));
                        }catch (NullPointerException nullp){}
                        break;
                    case "Gaussian Elimination":
                    case "Partial Pivoting":
                    case "Total Pivoting":
                    case "Staggered Pivoting":
                    case "Cholesky":
                    case "Crout":
                    case "Doolittle":
                    case "Gauss Seidel":
                    case "Jacobi":
                        preferences = getSharedPreferences("EquationsSystems",Context.MODE_PRIVATE);
                        try {
                            Load.load(preferences,returnthis());
                        }catch (Exception e){
                            Toast.makeText(returnthis().getApplicationContext(),
                                    e.toString(),Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "Newton Polynomial":
                    case "Lagrange Polynomial":
                    case "Neville":
                    case "Linear Spline":
                    case "Cubic Spline":
                        preferences = getSharedPreferences("Interpolation",Context.MODE_PRIVATE);
                        try{
                            co.edu.eafit.equations.inputs.interpolation.Load.load(preferences,returnthis());
                        }catch(Exception e){
                            Toast.makeText(returnthis().getApplicationContext(),
                                    e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    default:
                        break;
                }

                Snackbar.make(view, "Loading Data...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tabs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment tab = null;
            switch (position) {
                case 0: //Entradas
                    if(fragmentInput!=null){
                        return fragmentInput;
                    }else{
                        switch (nameToolbar){
                            case "Incremental Searches":
                                fragmentInput = InputIncrementalSearches.newInstance();
                                break;
                            case "Bisection":
                                fragmentInput = InputBisection.newInstance();
                                break;
                            case "False Position":
                                fragmentInput = InputFalsePosition.newInstance();
                                break;
                            case "Fixed Point":
                                fragmentInput = InputFixedPoint.newInstance();
                                break;
                            case "Newton":
                                fragmentInput = InputNewton.newInstance();
                                break;
                            case "Secant":
                                fragmentInput = InputSecant.newInstance();
                                break;
                            case "Multiple Roots":
                                fragmentInput = InputMultipleRoots.newInstance();
                                break;
                            case "Gaussian Elimination":
                                fragmentInput = InputGaussianElimination.newInstance();
                                break;
                            case "Cholesky":
                                fragmentInput = InputCholesky.newInstance();
                                break;
                            case "Crout":
                                fragmentInput = InputCrout.newInstance();
                                break;
                            case "Doolittle":
                                fragmentInput = InputDoolittle.newInstance();
                                break;
                            case "Gauss Seidel":
                                fragmentInput = InputGaussSeidel.newInstance();
                                break;
                            case "Jacobi":
                                fragmentInput = InputJacobi.newInstance();
                                break;
                            //Interpolation
                            case "Newton Polynomial":
                                fragmentInput = InputNewtonPolynomial.newInstance();
                                break;
                            case "Lagrange Polynomial":
                                fragmentInput = InputLagrangePolynomial.newInstance();
                                break;
                            //Added Value
                            case "Partial Pivoting":
                                fragmentInput = InputGEWithPartialPivoting.newInstance();
                                break;
                            case "Total Pivoting":
                                fragmentInput = InputGEWithTotalPivoting.newInstance();
                                break;
                            case "Staggered Pivoting":
                                fragmentInput = InputGEWithStaggeredPivoting.newInstance();
                                break;
                            case "Neville":
                                fragmentInput = InputNeville.newInstance();
                                break;
                            case "Linear Spline":
                                fragmentInput = InputLinearSpline.newInstance();
                                break;
                            case "Cubic Spline":
                                fragmentInput = InputCubicSpline.newInstance();
                                break;
                            default:
                                fragmentInput = null;
                                break;
                        }
                        return fragmentInput;
                    }
                case 1:
                    if(fragmentTable!=null){
                        return fragmentTable;
                    }else {
                        switch (nameToolbar) {
                            //Single Variable Equations
                            case "Incremental Searches":
                                fragmentTable = TableIncrementalSearches.newInstance();
                                break;
                            case "Bisection":
                                fragmentTable = TableBisection.newInstance();
                                break;
                            case "False Position":
                                fragmentTable = TableFalsePosition.newInstance();
                                break;
                            case "Fixed Point":
                                fragmentTable = TableFixedPoint.newInstance();
                                break;
                            case "Newton":
                                fragmentTable = TableNewton.newInstance();
                                break;
                            case "Secant":
                                fragmentTable = TableSecant.newInstance();
                                break;
                            case "Multiple Roots":
                                fragmentTable = TableMultipleRoots.newInstance();
                                break;
                            //Equations Systems
                            case "Gaussian Elimination":
                                fragmentTable = TableGaussianElimination.newInstance();
                                break;
                            case "Cholesky":
                                fragmentTable = TableCholesky.newInstance();
                                break;
                            case "Crout":
                                fragmentTable = TableCrout.newInstance();
                                break;
                            case "Doolittle":
                                fragmentTable = TableDoolittle.newInstance();
                                break;
                            case "Gauss Seidel":
                                fragmentTable = TableGaussSeidel.newInstance();
                                break;
                            case "Jacobi":
                                fragmentTable = TableJacobi.newInstance();
                                break;
                            //Interpolation
                            case "Newton Polynomial":
                                fragmentTable = TableNewtonPolynomial.newInstance();
                                break;
                            case "Lagrange Polynomial":
                                fragmentTable = TableLagrangePolynomial.newInstance();
                                break;
                            //Added Value
                            case "Partial Pivoting":
                                fragmentTable = TableGaussianEliminationWithPartialPivoting.newInstance();
                                break;
                            case "Total Pivoting":
                                fragmentTable = TableGEWithTotalPivoting.newInstance();
                                break;
                            case "Staggered Pivoting":
                                fragmentTable = TableGaussianEliminationWithStaggeredPivoting.newInstance();
                                break;
                            case "Neville":
                                fragmentTable = TableNeville.newInstance();
                                break;
                            case "Linear Spline":
                                fragmentTable = TableLinearSpline.newInstance();
                                break;
                            case "Cubic Spline":
                                fragmentTable = TableCubicSpline.newInstance();
                                break;
                            default:
                                fragmentTable = null;
                                break;
                        }
                        return fragmentTable;
                    }
                case 2:
                    if(fragmentHelp!=null){
                        return fragmentHelp;
                    }else{
                        switch (nameToolbar){
                            //Usar por si nesecita un help en especifico
                            default:
                                fragmentHelp =  Help.newInstance();
                                break;
                        }
                        return fragmentHelp;
                        //break;
                    }
                default:
                    Log.i("ErrorMenu:","Creacion de Frament Invalida");
            }
            return tab;
        }

        @Override
        public int getCount() { //Numero de paginas
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Input";
                case 1:
                    return "Table";
                case 2:
                    return "Help";
            }
            return "Default";
        }
    }

}
