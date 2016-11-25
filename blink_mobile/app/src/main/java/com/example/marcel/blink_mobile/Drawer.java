package com.example.marcel.blink_mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.marcel.blink_mobile.models.Usuario;

import java.io.Serializable;

public class Drawer extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, Serializable {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment = new NavigationDrawerFragment();

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        mNavigationDrawerFragment.setArguments(b);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment fragment = null;

        Intent i = getIntent();

        Usuario usuario = (Usuario) i.getSerializableExtra("Usuario");
        Bundle bs = new Bundle();
        bs.putSerializable("Usuario", usuario);

        Bundle b = i.getExtras();
        int value = -1; // or other values
        if(b != null)
            value = b.getInt("key");

        if(value == 0) {
            switch (position){
                case 0:
                    mTitle = getString(R.string.home);
                    fragment = new VendedorHome();

                    /*Intent i = getIntent();
                    Usuario usuario = (Usuario) i.getSerializableExtra("Usuario");
                    UserData userData = usuario.getUserData();

                    Log.d("UsuarioVendedor", userData.toString());*/

                    break;
                case 1:
                    mTitle = "Contas";
                    fragment = new ContasBancarias();
                    break;

                case 2:
                    mTitle = "Meus Dados";
                    fragment = new DadosVendedor();
                    break;

                case 3:
                    mTitle = "Meus Estabelecimentos";
                    fragment = new EstabelecimentosComerciais();
                    break;

                default:
                    break;
            }
        } else {
            switch (position){
                case 0:
                    mTitle = getString(R.string.home);
                    fragment = new ClienteHome();

                    break;
                case 1:
                    mTitle = "Cartões";
                    fragment = new Cartoes();
                    break;

                case 2:
                    mTitle = "Meus Dados";
                    fragment = new DadosCliente();
                    break;

                default:
                    break;
            }
        }
        fragment.setArguments(bs);
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.drawer, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_drawer, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((Drawer) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
