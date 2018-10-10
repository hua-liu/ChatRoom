package cn.hua.web.filter;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/CharSetFilter")
public class CharSetFilter implements Filter {
	public void destroy() {
		// TODO Auto-generated method stub
	}
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) resp;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		//chain.doFilter(request, response);
		//ResponseProxy proxy = new ResponseProxy(response);
		//MyResponse myResponse = new MyResponse(response);
		chain.doFilter(new RequestProxy(request).createProxy(),response);	//代理后放行
		
		/*byte[] buf = myResponse.getBuffer();
		//对数据进行压缩处理
		byte[] gzip = getGzip(buf);
		//通知浏览器文件压缩方式
		response.setHeader("content-encoding", "gzip");
		response.setHeader("content-length", gzip.length+"");
		response.getOutputStream().write(gzip);*/
	}
	/*public byte[] getGzip(byte[] buf) throws IOException{
		ByteArrayOutputStream bout = new ByteArrayOutputStream();//new一个字节数组缓冲流
		GZIPOutputStream gzipOut = new GZIPOutputStream(bout);	//new一个压缩流，将字节流作为容器
		gzipOut.write(buf);	//写buf写入字节缓冲流
		gzipOut.close();//关闭压缩流
		return bout.toByteArray();	//返回字节数组
	}*/
	public String filter(String message){	//转义方法
		if(message == null){
			return null;
		}
		char content[] = new char[message.length()];
		message.getChars(0, message.length(), content, 0);
		StringBuffer result = new StringBuffer(content.length);
		for(int i=0;i<content.length;i++){
			switch(content[i]){
			case '<':
				result.append("&lt;");
				break;
			case '>':
				result.append("&gt;");
				break;
			case '&':
				result.append("&amp;");
				break;
			case '"':
				result.append("&quot;");
				break;
			default:
				result.append(content[i]);
			}
		}
		return result.toString();
	}
	class RequestProxy{		//进行转义
		HttpServletRequest request;
		public RequestProxy(HttpServletRequest request){
			this.request = request;
		}
		public HttpServletRequest createProxy(){	//创建代理
			return (HttpServletRequest) Proxy.newProxyInstance(RequestProxy.class.getClassLoader(), request.getClass().getInterfaces(), new InvocationHandler(
					) {
				@Override
				public Object invoke(Object proxy, Method method, Object[] args)
						throws Throwable {
					if(!method.getName().equals("getParameter")){	//如果不是调用的getParameter方法则直接调用本身方法执行
						return method.invoke(request, args);
					}
					String value = (String) method.invoke(request, args);	//获取该方法的值
					return filter(value);
				}
			});
		}
	}
	/*class MyResponse extends HttpServletResponseWrapper{
		private ByteArrayOutputStream bout = new ByteArrayOutputStream();
		private PrintWriter pw;
		private HttpServletResponse response;	//接收传过来的response
		public MyResponse(HttpServletResponse response) {
			super(response);
			this.response = response;	
		}
		@Override
		public ServletOutputStream getOutputStream() throws IOException {	//重写方法
			return new MyServletOutputStream(bout);	//new一个servletoutputStream包装流
		}
		
		@Override
		public PrintWriter getWriter() throws IOException {		//重写方法
			pw = new PrintWriter(bout);
			return pw;
		}
		public byte[] getBuffer(){
			if(pw!=null){
				pw.close();	//为了保证所有数据能写入底层，此处关闭流
			}
			return bout.toByteArray();
		}
	}
	class MyServletOutputStream extends ServletOutputStream{
		private ByteArrayOutputStream bout;
		public MyServletOutputStream(ByteArrayOutputStream bout){
			this.bout = bout;
		}
		@Override
		public void write(int b) throws IOException {	//重写方法
			bout.write(b);
		}

		@Override
		public boolean isReady() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void setWriteListener(WriteListener arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}*/
	/*class ResponseProxy{	//response代理
		private HttpServletResponse response;
		private ByteArrayOutputStream bout = new ByteArrayOutputStream();	//一个字节数据流
		private PrintWriter pw = null;		
		public byte[] getBuffer(){	//因为printWriter数据数据小可能无法写出，所以在此定义getButffer方法
			if(pw!=null){
				pw.close();
			}
			System.out.println(bout.size());
			return bout.toByteArray();
		}
		public ResponseProxy(HttpServletResponse response){
			this.response = response;
		}
		public HttpServletResponse createProxy(){	//创建代理
			return (HttpServletResponse) Proxy.newProxyInstance(ResponseProxy.class.getClassLoader(), response.getClass().getInterfaces(), new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)throws Throwable {
				if(!method.getName().equals("getWriter") && !method.getName().equals("getOutputStream")){	//如果调用的不是这两个方法则调用自身方法
					 method.invoke(response, args);
				}
				if(method.getName().equals("getWriter")){
					pw =  new PrintWriter(bout);	//这里可能会出现乱码，所以使用低层流进行码指定
					System.out.println(bout.size());
					return pw;
				}
				if(method.getName().equals("getOutputStream")){	
					return new ServletOutputStream(){
						@Override
						public void write(int b) throws IOException {
							bout.write(b);
						}
						@Override
						public boolean isReady() {
							return false;
						}

						@Override
						public void setWriteListener(WriteListener arg0) {}
				};
			}
				return null;
		}});
		}
	}*/
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
