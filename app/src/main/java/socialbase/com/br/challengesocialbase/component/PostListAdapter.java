package socialbase.com.br.challengesocialbase.component;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import socialbase.com.br.challengesocialbase.DetailActivity;
import socialbase.com.br.challengesocialbase.R;
import socialbase.com.br.challengesocialbase.model.Post;
import socialbase.com.br.challengesocialbase.util.Constants;
import socialbase.com.br.challengesocialbase.util.Typefaces;

public class PostListAdapter extends ArrayAdapter<Post> implements View.OnClickListener, Constants {
	Context context;
	int layoutResourceId;
	private Typeface typeface;
	private SearchFilter filter;
	ArrayList<Post> postList;
	ArrayList<Post> originalList;

	public PostListAdapter(Context context, int layoutResourceId,
						   ArrayList<Post> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.typeface = Typefaces.ralewayTypeface(context);

		this.originalList = new ArrayList<>();
		this.originalList.addAll(data);
		this.postList = originalList;
	}

	@Override
	public Filter getFilter() {
		if (filter == null){
			filter  = new SearchFilter();
		}
		return filter;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ListHolder holder = null;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new ListHolder();
			holder.txtTitulo = (TextView) row.findViewById(R.id.txt_titulo);
			holder.imageItem = (ImageView) row.findViewById(R.id.item_image);
			row.setTag(holder);
		} else {
			holder = (ListHolder) row.getTag();
		}

		if (position % 2 == 0) {
			row.setBackgroundColor(context.getResources().getColor(R.color.listitemcolor1));
		} else {
			row.setBackgroundColor(context.getResources().getColor(R.color.listitemcolor2));
		}

		Post item = postList.get(position);
		holder.holder = item;

		holder.txtTitulo.setText(item.getTitle());
		holder.txtTitulo.setTypeface(typeface);
		if (item.getImage() != null && item.getImage().getMedium() != null) {
			Picasso.with(context)
					.load(item.getImage().getMedium())
					.placeholder(R.drawable.placeholder)
					.into(holder.imageItem);
		} else {
			holder.imageItem.setImageResource(R.drawable.placeholder);
		}

		row.setOnClickListener(this);
		
		return row;
	}

	@Override
	public void onClick(View v) {
		Post item = ((ListHolder) v.getTag()).holder;
		Intent intent = new Intent(context, DetailActivity.class);
		intent.putExtra(KEY_DETAIL, item);
		context.startActivity(intent);
	}

	static class ListHolder {
		TextView txtTitulo;
		ImageView imageItem;
		Post holder;
	}

	private class SearchFilter extends Filter {
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {

			constraint = constraint.toString().toLowerCase();
			FilterResults result = new FilterResults();

			if(constraint != null && constraint.toString().length() > 0)
			{
				ArrayList<Post> filteredItems = new ArrayList<Post>();

				for(int i = 0, l = originalList.size(); i < l; i++)
				{
					Post post = originalList.get(i);
					if(post.getTitle().toString().toLowerCase().contains(constraint)) {
						filteredItems.add(post);
					}
				}
				result.count = filteredItems.size();
				result.values = filteredItems;
			}
			else
			{
				synchronized(this)
				{
					result.values = originalList;
					result.count = originalList.size();
				}
			}
			return result;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,
									  FilterResults results) {

			postList = (ArrayList<Post>)results.values;
			notifyDataSetChanged();
			clear();
			for(int i = 0, l = postList.size(); i < l; i++) {
				add(postList.get(i));
			}
			notifyDataSetInvalidated();
		}
	}

}
