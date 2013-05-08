package vs.piratenpartei.ch.parser.forum;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.XMLReader;

import vs.piratenpartei.ch.app.forum.ThreadItem;
import vs.piratenpartei.ch.app.forum.ThreadItemCollection;
import vs.piratenpartei.ch.app.forum.TopicItem;
import vs.piratenpartei.ch.app.forum.TopicItemCollection;
import vs.piratenpartei.ch.parser.AbstractWebParser;

import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Html;
import android.util.Log;

public class ForumParser extends AbstractWebParser
{
	private static final String TAG = "ForumParser";

	public static final String SUBJECT 	= "subject";
	public static final String STARTER 	= "starter";
	public static final String VIEWS	= "views";
	public static final String POSTS	= "posts";
	public static final String LAST_UPDATE_DATE = "last_update";
	public static final String LAST_MESSAGE_LINK = "last_message";
	public static final String LAST_UPDATE_AUTHOR = "last_update_author";
	public static final String COMPLETE_POST = "complete_post";
	public static final String POST_AUTHOR = "post_author";
	public static final String POST_AUTHOR_AVATAR = "post_author_avatar";
	public static final String POST_CONTENT = "post_content";
	public static final String POST_DATE = "post_date";
	public static final String LAST_BOARD_LINK = "last_board_link";

	private static final String c_selectorSubject = "div.topic_table td.subject span a";
	private static final String c_selectorStarter = "div.topic_table td.subject p>a";
	private static final String c_selectorViews = "div.topic_table td.stats";
	private static final String c_selectorPosts = "div.topic_table td.stats";
	private static final String c_selectorLastUpdateDate = "div.topic_table td.lastpost";
	private static final String c_selectorLastMessageLink = "div.topic_table td.lastpost a:first-child";
	private static final String c_selectorLastUpdateAuthor = "div.topic_table td.lastpost a:last-child";
	private static final String c_selectorCompletePost = "#forumposts div.post_wrapper";
	private static final String c_selectorPostAuthor = "div.poster h4>a";
	private static final String c_selectorPostAuthorAvatar = "div.poster img.avatar";
	private static final String c_selectorPostContent = "div.postarea div.post div.inner";
	private static final String c_selectorPostDate = "div.postarea div.keyinfo div.smalltext";
	private static final String c_selectorLastBoardLink = "div.pagesection a.navPages:last-child";

	private IParserProgress _progressEvent;

	public ForumParser()
	{
		this._selectors.put(ForumParser.SUBJECT, ForumParser.c_selectorSubject);
		this._selectors.put(ForumParser.STARTER, ForumParser.c_selectorStarter);
		this._selectors.put(ForumParser.VIEWS, ForumParser.c_selectorViews);
		this._selectors.put(ForumParser.POSTS, ForumParser.c_selectorPosts);
		this._selectors.put(ForumParser.LAST_UPDATE_DATE, ForumParser.c_selectorLastUpdateDate);
		this._selectors.put(ForumParser.LAST_MESSAGE_LINK, ForumParser.c_selectorLastMessageLink);
		this._selectors.put(ForumParser.LAST_UPDATE_AUTHOR, ForumParser.c_selectorLastUpdateAuthor);
		this._selectors.put(ForumParser.COMPLETE_POST, ForumParser.c_selectorCompletePost);
		this._selectors.put(ForumParser.POST_AUTHOR, ForumParser.c_selectorPostAuthor);
		this._selectors.put(ForumParser.POST_AUTHOR_AVATAR, ForumParser.c_selectorPostAuthorAvatar);
		this._selectors.put(ForumParser.POST_CONTENT, ForumParser.c_selectorPostContent);
		this._selectors.put(ForumParser.POST_DATE, ForumParser.c_selectorPostDate);
		this._selectors.put(ForumParser.LAST_BOARD_LINK, ForumParser.c_selectorLastBoardLink);
	}

	public void setOnProgressEvent(IParserProgress pProgressEvent)
	{
		this._progressEvent = pProgressEvent;
	}

	private void fireProgress(TopicItem pTopicItem)
	{
		if(this._progressEvent != null)
		{
			this._progressEvent.onProgress(pTopicItem);
		}
	}

	public Elements getSubjects()
	{
		return this.getElementsByPredefinedSelector(ForumParser.SUBJECT);
	}

	public Elements getStarters()
	{
		return this.getElementsByPredefinedSelector(ForumParser.STARTER);
	}

	public Elements getViews()
	{
		return this.getElementsByPredefinedSelector(ForumParser.VIEWS);
	}

	public Elements getPosts()
	{
		return this.getElementsByPredefinedSelector(ForumParser.POSTS);
	}

	public Elements getLastUpdateDates()
	{
		return this.getElementsByPredefinedSelector(ForumParser.LAST_UPDATE_DATE);
	}

	public Elements getLastMessageLink()
	{
		return this.getElementsByPredefinedSelector(ForumParser.LAST_MESSAGE_LINK);
	}

	public Elements getLastUpdateAuthors()
	{
		return this.getElementsByPredefinedSelector(ForumParser.LAST_UPDATE_AUTHOR);
	}

	public Elements getPostAuthors()
	{
		return this.getElementsByPredefinedSelector(ForumParser.POST_AUTHOR);
	}

	public Elements getPostAvatars()
	{
		return this.getElementsByPredefinedSelector(ForumParser.POST_AUTHOR_AVATAR);
	}

	public Elements getPostContents()
	{
		return this.getElementsByPredefinedSelector(ForumParser.POST_CONTENT);
	}

	public Elements getPostDates()
	{
		return this.getElementsByPredefinedSelector(ForumParser.POST_DATE);
	}

	public Elements getCompletePost()
	{
		return this.getElementsByPredefinedSelector(ForumParser.COMPLETE_POST);
	}

	public Element getLastBoardPageLink()
	{
		Elements result = this.getElementsByPredefinedSelector(ForumParser.LAST_BOARD_LINK);
		Log.i(TAG, "result.size() = " + result.size());
		if(result.size() > 0)
		{
			return result.get(0);
		}
		return null;
	}

	public ThreadItemCollection getBoard()
	{
		Log.d(TAG, "getBoard()");
		ThreadItemCollection result = new ThreadItemCollection();
		Elements subjects = this.getSubjects();
		Elements starters = this.getStarters();
		Elements lastMessageLink = this.getLastMessageLink();
		Elements updateDates = this.getLastUpdateDates();
		Elements updateAuthors = this.getLastUpdateAuthors();
		Element boardPage = this.getLastBoardPageLink();
		if(boardPage != null)
		{
			String boardLink = boardPage.attr("href");
			ForumLink lastBoardLink = ForumLink.parse(boardLink);
			ThreadItem.LastBoardOffset = lastBoardLink.getOffset();
		}
		for(int index = 0; index < subjects.size(); index++)
		{
			ThreadItem current = new ThreadItem();
			current.setTitle(subjects.get(index).text());
			current.setTopicLink(subjects.get(index).attr("href"));
			current.setStarter(starters.get(index).text());
			String lastLink = lastMessageLink.get(index).attr("href");
			ForumLink lnk = ForumLink.parse(lastLink);
			current.setLastPossibleOffset(lnk.getOffset());
			current.setLastUpdateDate(detectString(updateDates.get(index).text()));
			current.setLastUpdateAuthor(updateAuthors.get(index).text());
			result.add(current);
		}
		return result;
	}

	public TopicItemCollection getTopic() throws IOException
	{
		Log.d(TAG, "getTopic()");
		TopicItemCollection result = new TopicItemCollection();
		Elements posts = this.getCompletePost();
		for(int index = 0; index < posts.size(); index++)
		{
			Element post = posts.get(index);
			TopicItem current = new TopicItem();
			Elements authors = post.select(this.getPredefinedSelector(ForumParser.POST_AUTHOR));
			if(authors.size() > 0)
			{
				Element author = authors.get(0);
				current.setAuthor(author.text());
			}
			Elements avatars = post.select(this.getPredefinedSelector(ForumParser.POST_AUTHOR_AVATAR));
			if(avatars.size() > 0)
			{
				Element avatar = avatars.get(0);
				String avatarUrl = avatar.attr("src");
				URL avatarRef = new URL(avatarUrl);
				Drawable avatarDrawable = Drawable.createFromStream(avatarRef.openStream(), current.getAuthor());
				current.setAvatar(avatarDrawable);
			}
			Elements contents = post.select(this.getPredefinedSelector(ForumParser.POST_CONTENT));
			if(contents.size() > 0)
			{
				Element content = contents.get(0);
				current.setContent(Html.fromHtml(content.html(), new Html.ImageGetter() {

					@Override
					public Drawable getDrawable(String pSource) {
						Log.i(TAG, "Getting Image from: " + pSource);
						URL src;
						try {
							src = new URL(pSource);
							Drawable image = Drawable.createFromStream(src.openStream(), pSource);
							image.setBounds(0, 0, 0 + Math.max(image.getIntrinsicWidth(), 32), 0 + Math.max(image.getIntrinsicHeight(), 32));
							return image;
						} catch (IOException e) {
							Log.w(TAG, e.getMessage());
							e.printStackTrace();
						}
						return null;
					}
				}, new Html.TagHandler() {

					@Override
					public void handleTag(boolean pOpening, String pTag, Editable pOutput,
							XMLReader pXmlReader) {
						//do nothing
					}
				}));
			}
			Elements dates = post.select(this.getPredefinedSelector(ForumParser.POST_DATE));
			if(dates.size() > 0)
			{
				Element date = dates.get(0);
				current.setDate(Html.fromHtml(date.html()).toString());
			}
			result.add(current);
			this.fireProgress(current);
		}
		return result;
	}

	private static String detectString(String pInput)
	{
		Log.d(TAG, "detectString(String)");
		String[] tempSplitted = pInput.split("\\sby\\s");
		String date = tempSplitted[0];
		return date;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <Result> Result fillClass() 
	{
		Log.d(TAG, "fillClass()");
		Result result = null;
		ForumLink url = ForumLink.parse(this._url.toString());
		if(url.getType() == ForumLink.TYPE_BOARD)
		{
			result = (Result) this.getBoard();
		}
		else if(url.getType() == ForumLink.TYPE_TOPIC)
		{
			try {
				result = (Result)this.getTopic();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	public static Date convertForumDate(String pDate) throws ParseException
	{
		Date now = new Date();
		DateFormat nowDate = new SimpleDateFormat("d. MMMMM yyyy", Locale.ENGLISH);
		pDate.replace("Today", nowDate.format(now));
		DateFormat df = new SimpleDateFormat("d. MMMMM yyyy, HH:mm:ss", Locale.ENGLISH);
		return df.parse(pDate);
	}

	public interface IParserProgress
	{
		void onProgress(TopicItem pLoadedTopicItem);
	}
}
