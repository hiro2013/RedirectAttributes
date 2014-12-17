package com.sample.redirectAttributes.web.controller;

import java.util.Collections;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Handles requests for the application home page.
 *
 */
//@Controller
@Scope("session")
public abstract class ControllerSuper {

	protected final String ErrorView = "error";

	protected Boolean enableLocalize = null;
	protected String localize[] = null;
	protected String localize_lang[][] = null;

	/**
	 * 1xx Informational 情報
	 * リクエストは受け取られた。処理は継続される。
	 */

	/**
	 * 100 Continue
	 * 継続。クライアントはリクエストを継続できる。サーバがリクエストの最初の部分を受け取り、まだ拒否していないことを示す。
	 * 例として、クライアントがExpect: 100-continueヘッダをつけたリクエストを行い、それをサーバが受理した場合に返される。
	 */
	@ResponseStatus(value = HttpStatus.CONTINUE)
	public class HttpStatus_CONTINUE_Exception extends RuntimeException {
	}

	/**
	 * 101 Switching Protocols
	 * プロトコル切替え。サーバはリクエストを理解し、遂行のためにプロトコルの切替えを要求している。
	 */
	@ResponseStatus(value = HttpStatus.SWITCHING_PROTOCOLS)
	public class HttpStatus_SWITCHING_PROTOCOLS_Exception extends RuntimeException {
	}

	/**
	 * 102 Processing
	 * 処理中。WebDAVの拡張ステータスコード。処理が継続されて行われていることを示す。
	 */
	@ResponseStatus(value = HttpStatus.PROCESSING)
	public class HttpStatus_PROCESSING_Exception extends RuntimeException {
	}

	/**
	 * ?
	 */
	@ResponseStatus(value = HttpStatus.CHECKPOINT)
	public class HttpStatus_CHECKPOINT_Exception extends RuntimeException {
	}

	/**
	 * 2xx Success 成功
	 * リクエストは受け取られ、理解され、受理された。
	 */

	/**
	 * 201 Created
	 * 作成。リクエストは完了し、新たに作成されたリソースのURIが返される。
	 * 例: PUTメソッドでリソースを作成するリクエストを行ったとき、そのリクエストが完了した場合に返される。
	 */
	@ResponseStatus(value = HttpStatus.CREATED)
	public class HttpStatus_CREATED_Exception extends RuntimeException {
	}

	/**
	 * 202 Accepted
	 * 受理。リクエストは受理されたが、処理は完了していない。
	 * 例: PUTメソッドでリソースを作成するリクエストを行ったとき、サーバがリクエストを受理したものの、リソースの作成が完了していない場合に返される。
	 * バッチ処理向け。
	 */
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public class HttpStatus_ACCEPTED_Exception extends RuntimeException {
	}

	/**
	 * 203 Non-Authoritative Information
	 * 信頼できない情報。オリジナルのデータではなく、ローカルやプロキシ等からの情報であることを示す。
	 */
	@ResponseStatus(value = HttpStatus.NON_AUTHORITATIVE_INFORMATION)
	public class HttpStatus_NON_AUTHORITATIVE_INFORMATION_Exception extends RuntimeException {
	}

	/**
	 * 204 No Content
	 * 内容なし。リクエストを受理したが、返すべきレスポンスエンティティが存在しない場合に返される。
	 * 例: POSTメソッドでフォームの内容を送信したが、ブラウザの画面を更新しない場合に返される。
	 */
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public class HttpStatus_NO_CONTENT_Exception extends RuntimeException {
	}

	/**
	 * 205 Reset Content
	 * 内容のリセット。リクエストを受理し、ユーザエージェントの画面をリセットする場合に返される。
	 * 例: POSTメソッドでフォームの内容を送信した後、ブラウザの画面を初期状態に戻す場合に返される。
	 */
	@ResponseStatus(value = HttpStatus.RESET_CONTENT)
	public class HttpStatus_RESET_CONTENT_Exception extends RuntimeException {
	}

	/**
	 * 206 Partial Content
	 * 部分的内容。部分的GETリクエストを受理したときに、返される。
	 * 例: ダウンロードツール等で分割ダウンロードを行った場合や、レジュームを行った場合に返される。
	 */
	@ResponseStatus(value = HttpStatus.PARTIAL_CONTENT)
	public class HttpStatus_PARTIAL_CONTENT_Exception extends RuntimeException {
	}

	/**
	 * 207 Multi-Status
	 * 複数のステータス。WebDAVの拡張ステータスコード。
	 */
	@ResponseStatus(value = HttpStatus.MULTI_STATUS)
	public class HttpStatus_MULTI_STATUS_Exception extends RuntimeException {
	}

	/**
	 *
	 *
	 */
	@ResponseStatus(value = HttpStatus.ALREADY_REPORTED)
	public class HttpStatus_ALREADY_REPORTED_Exception extends RuntimeException {
	}

	/**
	 * 226 IM Used
	 * IM使用。Delta encoding in HTTPの拡張ステータスコード。
	 */
	@ResponseStatus(value = HttpStatus.IM_USED)
	public class HttpStatus_IM_USED_Exception extends RuntimeException {
	}

	/**
	 * 3xx Redirection リダイレクション
	 * リクエストを完了させるために、追加的な処理が必要。
	 */

	/**
	 * 300 Multiple Choices
	 * 複数の選択。リクエストしたリソースが複数存在し、ユーザやユーザーエージェントに選択肢を提示するときに返される。
	 * 具体例として、W3Cのhttp://www.w3.org/TR/xhtml11/DTD/xhtml11.html
	 */
	@ResponseStatus(value = HttpStatus.MULTIPLE_CHOICES)
	public class HttpStatus_MULTIPLE_CHOICES_Exception extends RuntimeException {
	}

	/**
	 * 301 Moved Permanently
	 * 恒久的に移動した。リクエストしたリソースが恒久的に移動されているときに返される。Location:ヘッダに移動先のURLが示されている。
	 * 例としては、ファイルではなくディレクトリに対応するURLの末尾に/を書かずにアクセスした場合に返される。具体例として、http://www.w3.
	 * org/TR
	 */
	@ResponseStatus(value = HttpStatus.MOVED_PERMANENTLY)
	public class HttpStatus_MOVED_PERMANENTLY_Exception extends RuntimeException {
	}

	/**
	 * 302 Found
	 * 発見した。リクエストしたリソースが一時的に移動されているときに返される。Location:ヘッダに移動先のURLが示されている。
	 * 元々はMoved Temporarily（一時的に移動した）で、本来はリクエストしたリソースが一時的にそのURLに存在せず、
	 * 別のURLにある場合に使用するステータスコードであった
	 * 。しかし、例えば掲示板やWikiなどで投稿後にブラウザを他のURLに転送したいときにもこのコードが使用されるようになったため
	 * 、302はFoundになり、新たに303,307が作成された。
	 */
	@ResponseStatus(value = HttpStatus.FOUND)
	public class HttpStatus_FOUND_Exception extends RuntimeException {
	}

	/**
	 *
	 *
	 */
	@ResponseStatus(value = HttpStatus.MOVED_TEMPORARILY)
	public class HttpStatus_MOVED_TEMPORARILY_Exception extends RuntimeException {
	}

	/**
	 * 303 See Other
	 * 他を参照せよ。リクエストに対するレスポンスが他のURLに存在するときに返される。Location:ヘッダに移動先のURLが示されている。
	 * リクエストしたリソースは確かにそのURLにあるが、他のリソースをもってレスポンスとするような場合に使用する。302の説明で挙げたような、
	 * 掲示板やWikiなどで投稿後にブラウザを他のURLに転送したいときに使われるべきコードとして導入された。
	 */
	@ResponseStatus(value = HttpStatus.SEE_OTHER)
	public class HttpStatus_SEE_OTHER_Exception extends RuntimeException {
	}

	/**
	 * 304 Not Modified
	 * 未更新。リクエストしたリソースは更新されていないことを示す。
	 * 例として、 If-Modified-Since:ヘッダを使用したリクエストを行い、そのヘッダに示された時間以降に更新がなかった場合に返される。
	 */
	@ResponseStatus(value = HttpStatus.NOT_MODIFIED)
	public class HttpStatus_NOT_MODIFIED_Exception extends RuntimeException {
	}

	/**
	 * 305 Use Proxy
	 * プロキシを使用せよ。レスポンスのLocation:ヘッダに示されるプロキシを使用してリクエストを行わなければならないことを示す。
	 */
	@ResponseStatus(value = HttpStatus.USE_PROXY)
	public class HttpStatus_USE_PROXY_Exception extends RuntimeException {
	}

	/**
	 * 307 Temporary Redirect
	 * 一時的リダイレクト。リクエストしたリソースは一時的に移動されているときに返される。Location:ヘッダに移動先のURLが示されている。
	 * 302の規格外な使用法が横行したため、302の本来の使用法を改めて定義したもの。
	 */
	@ResponseStatus(value = HttpStatus.TEMPORARY_REDIRECT)
	public class HttpStatus_TEMPORARY_REDIRECT_Exception extends RuntimeException {
	}

	/**
	 *
	 *
	 */
	@ResponseStatus(value = HttpStatus.RESUME_INCOMPLETE)
	public class HttpStatus_RESUME_INCOMPLETE_Exception extends RuntimeException {
	}

	/**
	 * 4xx Client Error クライアントエラー
	 * クライアントからのリクエストに誤りがあった。
	 */

	/**
	 * 400 Bad Request
	 * リクエストが不正である。定義されていないメソッドを使うなど、クライアントのリクエストがおかしい場合に返される。
	 */
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public class HttpStatus_BAD_REQUEST_Exception extends RuntimeException {
	}

	/**
	 * 401 Unauthorized
	 * 認証が必要である。Basic認証やDigest認証などを行うときに使用される。
	 * たいていのブラウザはこのステータスを受け取ると、認証ダイアログを表示する。
	 */
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public class HttpStatus_UNAUTHORIZED_Exception extends RuntimeException {
	}

	/**
	 * 402 Payment Required
	 * 支払いが必要である。現在は実装されておらず、将来のために予約されているとされる。
	 */
	@ResponseStatus(value = HttpStatus.PAYMENT_REQUIRED)
	public class HttpStatus_PAYMENT_REQUIRED_Exception extends RuntimeException {
	}

	/**
	 * 403 Forbidden
	 * 禁止されている。リソースにアクセスすることを拒否された。リクエストはしたが処理できないという意味。
	 * アクセス権がない場合や、ホストがアクセス禁止処分を受けた場合などに返される。
	 * 例: 社内（イントラネット）からのみアクセスできるページに社外からアクセスしようとした。
	 */
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	public class HttpStatus_FORBIDDEN_Exception extends RuntimeException {
	}

	/**
	 * 404 Not Found
	 * 未検出。リソースが見つからなかった。
	 * 単に、アクセス権がない場合などにも使用される。
	 */
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public class HttpStatus_NOT_FOUND_Exception extends RuntimeException {
	}

	/**
	 * 405 Method Not Allowed
	 * 許可されていないメソッド。許可されていないメソッドを使用しようとした。
	 * 例: POSTメソッドの使用が許されていない場所で、POSTメソッドを使用した場合に返される。
	 */
	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
	public class HttpStatus_METHOD_NOT_ALLOWED_Exception extends RuntimeException {
	}

	/**
	 * 406 Not Acceptable
	 * 受理できない。Accept関連のヘッダに受理できない内容が含まれている場合に返される。
	 * 例: サーバは英語か日本語しか受け付けられないが、リクエストのAccept-Language:ヘッダにzh（中国語）しか含まれていなかった。
	 * 例:
	 * サーバはapplication/pdfを送信したかったが、リクエストのAccept:ヘッダにapplication/pdfが含まれていなかった。
	 * 例: サーバはUTF-8の文章を送信したかったが、リクエストのAccept-Charset:ヘッダには、UTF-8が含まれていなかった。
	 */
	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
	public class HttpStatus_NOT_ACCEPTABLE_Exception extends RuntimeException {
	}

	/**
	 * 407 Proxy Authentication Required
	 * プロキシ認証が必要である。プロキシの認証が必要な場合に返される。
	 */
	@ResponseStatus(value = HttpStatus.PROXY_AUTHENTICATION_REQUIRED)
	public class HttpStatus_PROXY_AUTHENTICATION_REQUIRED_Exception extends RuntimeException {
	}

	/**
	 * 408 Request Timeout
	 * リクエストタイムアウト。リクエストが時間以内に完了していない場合に返される。
	 */
	@ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT)
	public class HttpStatus_REQUEST_TIMEOUT_Exception extends RuntimeException {
	}

	/**
	 * 409 Conflict
	 * 矛盾。要求は現在のリソースと矛盾するので完了出来ない。
	 */
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public class HttpStatus_CONFLICT_Exception extends RuntimeException {
	}

	/**
	 * 410 Gone
	 * 消滅した。ファイルは恒久的に移動した。どこに行ったかもわからない。
	 * 404 Not Foundと似ているが、こちらは二度と復活しない場合に示される。ただ、このコードは特殊な操作をしないと表示できないため、
	 * ファイルが消滅しても404コードを出すサイトが多い。
	 */
	@ResponseStatus(value = HttpStatus.GONE)
	public class HttpStatus_GONE_Exception extends RuntimeException {
	}

	/**
	 * 411 Length Required
	 * 長さが必要。Content-Length ヘッダがないのでサーバがアクセスを拒否した場合に返される。
	 */
	@ResponseStatus(value = HttpStatus.LENGTH_REQUIRED)
	public class HttpStatus_LENGTH_REQUIRED_Exception extends RuntimeException {
	}

	/**
	 * 412 Precondition Failed
	 * 前提条件で失敗した。前提条件が偽だった場合に返される。
	 * 例: リクエストのIf-Unmodified-Since:ヘッダに書いた時刻より後に更新があった場合に返される。
	 */
	@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
	public class HttpStatus_PRECONDITION_FAILED_Exception extends RuntimeException {
	}

	/**
	 * 413 Request Entity Too Large
	 * リクエストエンティティが大きすぎる。リクエストエンティティがサーバの許容範囲を超えている場合に返す。
	 * 例: アップローダの上限を超えたデータを送信しようとした。
	 */
	@ResponseStatus(value = HttpStatus.REQUEST_ENTITY_TOO_LARGE)
	public class HttpStatus_REQUEST_ENTITY_TOO_LARGE_Exception extends RuntimeException {
	}

	/**
	 * 414 Request-URI Too Long
	 * リクエストURIが大きすぎる。URIが長過ぎるのでサーバが処理を拒否した場合に返す。
	 * 例: 画像データのような大きなデータをGETメソッドで送ろうとし、URIが何10kBにもなった場合に返す（上限はサーバに依存する）。
	 */
	@ResponseStatus(value = HttpStatus.REQUEST_URI_TOO_LONG)
	public class HttpStatus_REQUEST_URI_TOO_LONG_Exception extends RuntimeException {
	}

	/**
	 * 415 Unsupported Media Type
	 * サポートしていないメディアタイプ。指定されたメディアタイプがサーバでサポートされていない場合に返す。
	 */
	@ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	public class HttpStatus_UNSUPPORTED_MEDIA_TYPE_Exception extends RuntimeException {
	}

	/**
	 * 416 Requested Range Not Satisfiable
	 * リクエストしたレンジは範囲外にある。実ファイルのサイズを超えるデータを要求した。
	 * たとえば、リソースのサイズが1024Byteしかないのに、1025Byteを取得しようとした場合などに返す。
	 */
	@ResponseStatus(value = HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE)
	public class HttpStatus_REQUESTED_RANGE_NOT_SATISFIABLE_Exception extends RuntimeException {
	}

	/**
	 * 417 Expectation Failed
	 * Expectヘッダによる拡張が失敗。その拡張はレスポンスできない。またはプロキシサーバは、次に到達するサーバがレスポンスできないと判断している。
	 * 具体例として、Expect:ヘッダに100-continue以外の変なものを入れた場合や、そもそもサーバが100
	 * Continueを扱えない場合に返す。
	 */
	@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
	public class HttpStatus_EXPECTATION_FAILED_Exception extends RuntimeException {
	}

	/**
	 * 418 I'm a teapot
	 * 私はティーポット。HTCPCP/1.0の拡張ステータスコード。
	 * ティーポットにコーヒーを淹れさせようとして、拒否された場合に返すとされる、ジョークのコードである。
	 */
	@ResponseStatus(value = HttpStatus.I_AM_A_TEAPOT)
	public class HttpStatus_I_AM_A_TEAPOT_Exception extends RuntimeException {
	}

	/**
	 *
	 *
	 */
	@ResponseStatus(value = HttpStatus.INSUFFICIENT_SPACE_ON_RESOURCE)
	public class HttpStatus_INSUFFICIENT_SPACE_ON_RESOURCE_Exception extends RuntimeException {
	}

	/**
	 *
	 *
	 */
	@ResponseStatus(value = HttpStatus.METHOD_FAILURE)
	public class HttpStatus_METHOD_FAILURE_Exception extends RuntimeException {
	}

	/**
	 *
	 *
	 */
	@ResponseStatus(value = HttpStatus.DESTINATION_LOCKED)
	public class HttpStatus_DESTINATION_LOCKED_Exception extends RuntimeException {
	}

	/**
	 * 422 Unprocessable Entity
	 * 処理できないエンティティ。WebDAVの拡張ステータスコード。
	 */
	@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
	public class HttpStatus_UNPROCESSABLE_ENTITY_Exception extends RuntimeException {
	}

	/**
	 * 423 Locked
	 * ロックされている。WebDAVの拡張ステータスコード。リクエストしたリソースがロックされている場合に返す。
	 */
	@ResponseStatus(value = HttpStatus.LOCKED)
	public class HttpStatus_LOCKED_Exception extends RuntimeException {
	}

	/**
	 * 424 Failed Dependency
	 * 依存関係で失敗。WebDAVの拡張ステータスコード。
	 */
	@ResponseStatus(value = HttpStatus.FAILED_DEPENDENCY)
	public class HttpStatus_FAILED_DEPENDENCY_Exception extends RuntimeException {
	}

	/**
	 * 426 Upgrade Required
	 * アップグレード要求。Upgrading to TLS Within HTTP/1.1の拡張ステータスコード。
	 */
	@ResponseStatus(value = HttpStatus.UPGRADE_REQUIRED)
	public class HttpStatus_UPGRADE_REQUIRED_Exception extends RuntimeException {
	}

	/**
	 *
	 *
	 */
	@ResponseStatus(value = HttpStatus.PRECONDITION_REQUIRED)
	public class HttpStatus_PRECONDITION_REQUIRED_Exception extends RuntimeException {
	}

	/**
	 *
	 *
	 */
	@ResponseStatus(value = HttpStatus.TOO_MANY_REQUESTS)
	public class HttpStatus_TOO_MANY_REQUESTS_Exception extends RuntimeException {
	}

	/**
	 *
	 *
	 */
	@ResponseStatus(value = HttpStatus.REQUEST_HEADER_FIELDS_TOO_LARGE)
	public class HttpStatus_REQUEST_HEADER_FIELDS_TOO_LARGE_Exception extends RuntimeException {
	}

	/**
	 * 5xx Server Error サーバエラー
	 * サーバがリクエストの処理に失敗した。
	 */

	/**
	 * 500 Internal Server Error
	 * サーバ内部エラー。サーバ内部にエラーが発生した場合に返される。
	 * 例として、CGIとして動作させているプログラムに文法エラーがあったり、設定に誤りがあった場合などに返される。
	 */
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public class HttpStatus_INTERNAL_SERVER_ERROR_Exception extends RuntimeException {
	}

	/**
	 * 501 Not Implemented
	 * 実装されていない。実装されていないメソッドを使用した。
	 * 例として、WebDAVが実装されていないサーバに対してWebDAVで使用するメソッド（MOVEやCOPY）を使用した場合に返される。
	 */
	@ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED)
	public class HttpStatus_NOT_IMPLEMENTED_Exception extends RuntimeException {
	}

	/**
	 * 502 Bad Gateway
	 * 不正なゲートウェイ。ゲートウェイ・プロキシサーバは不正な要求を受け取り、これを拒否した。
	 */
	@ResponseStatus(value = HttpStatus.BAD_GATEWAY)
	public class HttpStatus_BAD_GATEWAY_Exception extends RuntimeException {
	}

	/**
	 * 503 Service Unavailable
	 * サービス利用不可。サービスが一時的に過負荷やメンテナンスで使用不可能である。
	 * 例として、アクセスが殺到して処理不能に陥った場合に返される。
	 */
	@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
	public class HttpStatus_SERVICE_UNAVAILABLE_Exception extends RuntimeException {
	}

	/**
	 * 504 Gateway Timeout
	 * ゲートウェイタイムアウト。ゲートウェイ・プロキシサーバはURIから推測されるサーバからの適切なレスポンスがなくタイムアウトした。
	 */
	@ResponseStatus(value = HttpStatus.GATEWAY_TIMEOUT)
	public class HttpStatus_GATEWAY_TIMEOUT_Exception extends RuntimeException {
	}

	/**
	 * 505 HTTP Version Not Supported
	 * サポートしていないHTTPバージョン。リクエストがサポートされていないHTTPバージョンである場合に返される。
	 */
	@ResponseStatus(value = HttpStatus.HTTP_VERSION_NOT_SUPPORTED)
	public class HttpStatus_HTTP_VERSION_NOT_SUPPORTED_Exception extends RuntimeException {
	}

	/**
	 * 506 Variant Also Negotiates
	 * Transparent Content Negotiation in HTTPで定義されている拡張ステータスコード。
	 */
	@ResponseStatus(value = HttpStatus.VARIANT_ALSO_NEGOTIATES)
	public class HttpStatus_VARIANT_ALSO_NEGOTIATES_Exception extends RuntimeException {
	}

	/**
	 * 507 Insufficient Storage
	 * 容量不足。WebDAVの拡張ステータスコード。リクエストを処理するために必要なストレージの容量が足りない場合に返される。
	 */
	@ResponseStatus(value = HttpStatus.INSUFFICIENT_STORAGE)
	public class HttpStatus_INSUFFICIENT_STORAGE_Exception extends RuntimeException {
	}

	/**
	 *
	 *
	 */
	@ResponseStatus(value = HttpStatus.LOOP_DETECTED)
	public class HttpStatus_LOOP_DETECTED_Exception extends RuntimeException {
	}

	/**
	 * 509 Bandwidth Limit Exceeded
	 * 帯域幅制限超過。そのサーバに設定されている帯域幅（転送量）を使い切った場合に返される。
	 */
	@ResponseStatus(value = HttpStatus.BANDWIDTH_LIMIT_EXCEEDED)
	public class HttpStatus_BANDWIDTH_LIMIT_EXCEEDED_Exception extends RuntimeException {
	}

	/**
	 * 510 Not Extended
	 * 拡張できない。An HTTP Extension Frameworkで定義されている拡張ステータスコード。
	 */
	@ResponseStatus(value = HttpStatus.NOT_EXTENDED)
	public class HttpStatus_NOT_EXTENDED_Exception extends RuntimeException {
	}

	/**
	 *
	 *
	 */
	@ResponseStatus(value = HttpStatus.NETWORK_AUTHENTICATION_REQUIRED)
	public class HttpStatus_NETWORK_AUTHENTICATION_REQUIRED_Exception extends RuntimeException {
	}

	protected String errorMessageView(Locale locale, HttpServletRequest request, String title, String message) {
		// 開始トレース
		System.out.println("start");
		try {
			String viewname = "redirect:/message";

			// TODO RedirectAttributesに値を設定したい
			//	HttpServletRequest request を RedirectAttributes ｒedirectAttributes に変更して
			//	ｒedirectAttributes.addAttribute("title", title);
			//	ｒedirectAttributes.addAttribute("message", message);
			// とやりたい

			request.setAttribute("title", title);
			request.setAttribute("message", message);

			return viewname;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			// 終了トレース
			System.out.println("return");
		}
	}

	/**
	 * DEBUG用：HttpServletRequestを表示する
	 * @param req
	 */
	protected void printHttpServletRequest(HttpServletRequest req) {
		// 開始トレース
		System.out.println("start");
		try {
			System.out.println(
					"*** HttpServletRequest  RemoteAddr=" + req.getRemoteAddr()
					);
			System.out.println(
					"*** HttpServletRequest  RemoteHost=" + req.getRemoteHost()
					);
			System.out.println(
					"*** HttpServletRequest  RemoteUser=" + req.getRemoteUser()
					);
			System.out.println(
					"*** HttpServletRequest  RequestedSessionId=" + req.getRequestedSessionId()
					);
			System.out.println(
					"*** HttpServletRequest Parameter Print Start [" + req.getRequestURI() + "]Method: "
							+ req.getMethod());

			String pname = null;
			for (Object obj : Collections.list(req.getParameterNames())) {
				pname = obj.toString();
				System.out.println(pname + ": " + req.getParameter(pname));
			}
			System.out.println(
					"*** HttpServletRequest Parameter Print End [" + req.getRequestURI() + "]Method: "
							+ req.getMethod());

			System.out
					.println(
					"*** HttpServletRequest Header Print Start [" + req.getRequestURI() + "]Method: " + req.getMethod());
			for (Object obj : Collections.list(req.getHeaderNames())) {
				pname = obj.toString();
				System.out.println(pname + ": " + req.getHeader(pname));
			}
			System.out.println(
					"*** HttpServletRequest Header Print End [" + req.getRequestURI() + "]Method: " + req.getMethod());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 終了トレース
			System.out.println("return");
		}
	}

}
