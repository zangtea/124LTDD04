package order;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import fragment.AccountFragment;
import fragment.NewsFragment;
import fragment.OrderFragment;

public class OrderViewPageAdapter extends FragmentStatePagerAdapter {
    public OrderViewPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Tab1Fragment();
            case 1:
                return new Tab2Fragment();
            case 2:
                return new Tab3Fragment();
            default:
                return new Tab1Fragment();
        }

    }

    @Override
    public int getCount() {

        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
   switch (position){
       case 0:
           return "Tab 1";
       case 1:
           return "Tab 2";
       case 2:
           return "Tab 3";
       default:
           return "Tab 1";
   }

    }
}
