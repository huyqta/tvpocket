//package com.mobile.tvpocket.adapter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.mobile.tvpocket.R;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.FragmentStatePagerAdapter;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//public class HomeDetailPagerAdapter extends FragmentStatePagerAdapter {
//
//	List<String[]> listHomeDetail = new ArrayList<String[]>();
//	
//
//	public HomeDetailPagerAdapter(FragmentManager fragmentManager) {
//		super(fragmentManager);
//		// TODO Auto-generated constructor stub
////		String[] homedetail = new String[]{"12:12 PM", "Pirates of Caribean"};
////		listHomeDetail.add(homedetail);
////		listHomeDetail.add(homedetail);
////		listHomeDetail.add(homedetail);
//	}
//
//	@Override
//	public Fragment getItem(int position) {
////		Fragment mHomeFragment = new HomeFragment(listTabs[position]);
////		Bundle args = new Bundle();
////		args.putInt(HomeFragment.Channel, position + 1); // Our object is just
////															// an integer :-P
////		mHomeFragment.setArguments(args);
//		return null;
//	}
//
//	@Override
//	public CharSequence getPageTitle(int position) {
//		return null;
//	}
//
//	@Override
//	public int getCount() {
//		// TODO Auto-generated method stub
//		return listHomeDetail.size();
//	}
//
//	// //////////////////////////////////////
//	// ----- ChannelCatalogFragment ------//
//	// //////////////////////////////////////
//	public static class HomeDetailFragment extends Fragment {
//
//		public static String Channel;
//
//		public HomeDetailFragment(String channel) {
//			this.Channel = channel;
//		}
//
//		@Override
//		public View onCreateView(LayoutInflater inflater,
//				@Nullable ViewGroup container,
//				@Nullable Bundle savedInstanceState) {
//			View rootView = inflater.inflate(R.layout.fragment_home, container,
//					false);
//			//ListView listview_detail = (ListView)rootView.findViewById(R.id.listview_fragment_home_detail);
//			//listview_detail.setAdapter(adapter)
//			Bundle args = getArguments();
//			return rootView;
//		}
//
//	}	
//}
