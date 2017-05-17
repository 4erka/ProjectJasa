package com.akujasa.jasacenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static android.content.ContentValues.TAG;


public class StatusPesananTokoFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3;
    String idToko;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View v = inflater.inflate(R.layout.status_pesanan_fragment, container, false);
        tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);

        /**
         *Set an Apater for the View Pager
         */
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        idToko = getArguments().getString("idToko");
        Log.e(TAG, "status pesanan fragment: " + idToko);

        return v;

    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("idToko", idToko);
                    StatusPesananTokoBaru statusPesananTokoBaru = new StatusPesananTokoBaru();
                    statusPesananTokoBaru.setArguments(bundle1);
                    return statusPesananTokoBaru;
                    //return new StatusPesananTokoBaru();
                case 1:
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("idToko", idToko);
                    StatusPesananTokoProgress statusPesananTokoProgress = new StatusPesananTokoProgress();
                    statusPesananTokoProgress.setArguments(bundle2);
                    return statusPesananTokoProgress;
                    //return new StatusPesananTokoProgress();
                case 2:
                    Bundle bundle3 = new Bundle();
                    bundle3.putString("idToko", idToko);
                    StatusPesananTokoSelesai statusPesananTokoSelesai = new StatusPesananTokoSelesai();
                    statusPesananTokoSelesai.setArguments(bundle3);
                    return statusPesananTokoSelesai;
                    //return new StatusPesananTokoSelesai();
            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    String baru = "BARU";
                    return baru;
                case 1:
                    String progress = "PROGRESS";
                    return progress;
                case 2:
                    String selesai = "SELESAI";
                    return selesai;
            }
            return null;
        }
    }

}