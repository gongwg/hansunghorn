package SOD.SmartTV;

import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

import SOD.Common.Packet;
import SOD.Common.ReceiveHandler;
import SOD.Common.ThreadEx;
import SOD.Common.Transceiver;
import SOD.Common.Tuple;
import SOD.Test.ActionEx;

/**
 * 
 * @author MB
 *
 */
public class AccessManagerServer {
	protected final int CheckingTerm = 4000;
	
	protected ConnectHandler cb_conn;
	protected DisconnectHandler cb_disc;
	protected ServerReceiveHandler cb_recv;
	
	protected Transceiver listener;
	protected ServerConfig config;
	protected boolean isRunning = false;
	protected Map<Integer, Tuple<Transceiver, Long>> connset;
	
	public AccessManagerServer(){
		connset = new ConcurrentHashMap<Integer, Tuple<Transceiver,Long>>();
	}
	
	/**
	 * 서버 초기화 및 beginListening, beginCheckingConnection 호출
	 * @param conf
	 * 서버의 정보
	 * @throws IllegalArgumentException
	 * 매개변수가 null일때 발생
	 * @throws IllegalStateException
	 * setReceiveHandler()가 호출되지 않았거나 이미 이전에 start()가 호출되었다면 발생
	 */
	public void start(ServerConfig conf) throws IllegalArgumentException, IllegalStateException{
		if(conf == null) throw new IllegalArgumentException("argument conf should not be null.");
		if(isRunning) throw new IllegalStateException("already server is running.");
		
		this.config = conf;
		listener = new Transceiver(null, conf.Port);
		
		beginListening();
		beginCheckingConnection();		
	}

	/**
	 * beginListening, beginCheckingConnection에서 생성한 스레드를 종료하고,
	 * 콜백 함수를 전부 제거하고 할당된 모든 리소스 해제.
	 * @throws IllegalStateException
	 * start()가 호출되기 전에 호출되거나 두번 이상 호출될 시 발생
	 */
	public void shutdown() throws IllegalStateException{
		if(!isRunning) throw new IllegalStateException("server is not running.");
		
		isRunning = false;
		connset.clear();
	}

	/**
	 * 새 연결이 들어왔을떄 호출되는 콜백함수 등록
	 * @param handler
	 * 콜백함수를 구현한 객체
	 * @throws IllegalArgumentException
	 * 매개변수가 null일때 발생
	 */
	public void setConnectHandler(ConnectHandler handler) throws IllegalArgumentException{
		if(handler == null) throw new IllegalArgumentException("argument handler should not be null.");
		
		cb_conn = handler;
	}

	/**
	 * 연결이 끊겼을떄 호출되는 콜백함수 등록
	 * @param handler
	 * 콜백함수를 구현한 객체
	 * @throws IllegalArgumentException
	 * 매개변수가 null일때 발생
	 */
	public void setDisconnectHandler(DisconnectHandler handler) throws IllegalArgumentException{
		if(handler == null) throw new IllegalArgumentException("argument handler should not be null.");
		
		cb_disc = handler;
	}

	/**
	 * 현재 서버에 접속이 성립되어 생성된 Transceiver중 어떤 것이든 onReceive 콜백이 호출될때
	 * 전달된 Packet및 해당 Transceiver의 id를 넘겨주는 콜백 함수를 등록한다.
	 * @param handler
	 * 콜백함수를 구현한 객체
	 * @throws IllegalArgumentException
	 * 매개변수가 null일때 발생
	 */
	public void setReceiveHandler(ServerReceiveHandler handler) throws IllegalArgumentException{
		if(handler == null) throw new IllegalArgumentException("argument handler should not be null.");
		
		cb_recv = handler;
	}
	
	/**
	 * 컬렉션에 등록된 연결 갯수를 가져온다.
	 * @return
	 * 현재 붙어있는 연결 갯수
	 */
	public int getConnectionCount(){
		return connset.size();
	}

	/**
	 * Send a Packet via Transceiver which matching connid.
	 * @param pkt
	 * 데이터를 담은 패킷
	 * @param connid
	 * Transceiver 객체를 가르키는 id
	 * @throws IllegalArgumentException
	 * pkt이 null이거나 connid가 유효하지 않은 경우 발생
	 * @throws IllegalStateException
	 * start()를 호출하기 전에 호출되었을 경우 발생
	 * @throws SocketException
	 * 내부 Transceiver 객체 에서 예외가 발생시 전달
	 */
	public void send(Packet pkt, int connid) throws IllegalArgumentException, IllegalStateException, SocketException{
		if(!isRunning) throw new IllegalStateException("server is not running.");
		
		if(pkt == null) 
			throw new IllegalArgumentException("argument pkt should not be null.");
		if(!connset.containsKey(connid))
			throw new IllegalArgumentException("connid is not valid.");
		
		Transceiver t = connset.get(connid).item1;
		boolean result = t.send(pkt);
		if(result == false)
			 throw new SocketException();
	}

	/**
	 * 해당 id에 일치하는 Transceiver의 연결을 끊고 컬렉션에서 제거한다.
	 * @param connid
	 * Transceiver 객체를 가르키는 id
	 * @throws IllegalArgumentException
	 * connid가 유효하지 않은 경우 발생
	 */
	public void dropConnection(int connid) throws IllegalArgumentException{
		if(!connset.containsKey(connid))
			throw new IllegalArgumentException("connid is not valid.");
		
		connset.remove(connid);
	}

	/**
	 * 새로운 연결을 수신(Accept)하거나 데이터를 수신(Receive)하기 위한 작업 스레드를 생성한다.
	 * 연결이 될때마다 내부의 컬렉션에 연결이 추가된다.
	 */
	protected void beginListening(){
		ThreadEx.invoke(null, new ActionEx() {		
			
			@Override
			public void work(Object arg) {
				Packet p = new Packet();
				InetSocketAddress sender = null;
				Tuple<Transceiver, Long> t = null;
				while(isRunning){					
					sender = listener.receive(p);
					
					switch(p.signiture){
					case Packet.REQUEST_ACCEPT:
						t = new Tuple<Transceiver, Long>();
						t.item1 = new Transceiver(sender);
						t.item2 = ThreadEx.getCurrentTime();
						connset.put(sender.hashCode(), t);
						break;
					case Packet.RESPONSE_CLIENT_ALIVE:
						t = connset.get(sender.hashCode());
						t.item2 = ThreadEx.getCurrentTime();
						break;
					case Packet.REQUEST_SERVICE_DATA:
						//need to implement
						break;
					default:
						cb_recv.onReceive(p, sender.hashCode());
						break;
					}
				}
			}
			
		});		
	}

	/**
	 * 현재 컬렉션에 있는 각 Transceiver들을 열거하며 연결 점검용 패킷을 보내기 위한 작업 스레드를 생성한다.
	 * 열거도중 패킷이 마지막으로 응답한 시간과 현재 시간의 차이가 Timeout을 초과하면 dropConnection 호출.
	 */
	protected void beginCheckingConnection(){
		
		ThreadEx.invoke(null, new ActionEx() {
			
			@Override
			public void work(Object arg) {
				Queue<Integer> expired = new LinkedList<Integer>();
				Packet p = new Packet();
				p.signiture = Packet.REQUEST_CLIENT_ALIVE;
				
				while(isRunning){
					ThreadEx.sleep(CheckingTerm);
					
					Iterator<Integer> h = connset.keySet().iterator();
					while(h.hasNext()){
						Integer connid = h.next();
						Tuple<Transceiver, Long> t = connset.get(connid);
						long elapsed = ThreadEx.getCurrentTime() - t.item2;
						if(elapsed > config.Timeout)
							expired.offer(connid);
						else
							t.item1.send(p);
					}
					
					while(expired.size() > 0)
						connset.remove(expired.poll());
				}
			}
			
		});
		
	}
	
	/**
	 * 특정 Transceiver에게 이 서비스의 이름을 전송한다.
	 * @param name
	 * 서비스의 이름
	 * @param connid
	 * Transceiver 객체를 가르키는 id
	 * @throws IllegalArgumentException
	 * String NULL이거나 ,connid가 유효하지 않은 경우 발생
	 */
	protected void sendServiceName(String name, int connid) throws IllegalArgumentException{
		if(name == null) 
			throw new IllegalArgumentException("argument name should not be null.");
		if(!connset.containsKey(connid))
			throw new IllegalArgumentException("connid is not valid.");
		
		Packet p = new Packet();
		p.signiture = Packet.RESPONSE_SERVICE_NAME;
		p.push(name);
		
		Tuple<Transceiver, Long> t = connset.get(connid);
		t.item1.send(p);
	}

}
