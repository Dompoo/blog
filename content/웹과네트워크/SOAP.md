- Simple Object Access Protocol
- XML 기반의 구조화된 메시징 프로토콜이다.
- 엄격한 표준과 보안 기능을 제공한다.
- 주로 HTTP 위에서 작동하지만, 프로토콜 독립적이다.
- `WSDL` : 서비스의 기능, 메시지 형식, 엔드포인트들을 명시적으로 정의하는 것이다. 클라이언트가 서비스를 자동으로 이해하고 사용할 수 있도록 한다.
### 통신 프로세스
#### 서버 측에서 제공할 웹 서비스에 대한 WSDL을 작성한다.
```xml
<!-- 예시: 계좌 조회 서비스 WSDL -->
<definitions name="BankService"
  targetNamespace="http://bank.example.com/"
  xmlns:tns="http://bank.example.com/">
  
  <!-- 메시지(입출력) 타입 정의 -->
  <message name="GetBalanceRequest">
    <part name="accountNumber" type="xsd:string"/>
  </message>
  
  <message name="GetBalanceResponse">
    <part name="balance" type="xsd:decimal"/>
  </message>
  
  <!-- 오퍼레이션 정의 -->
  <portType name="BankServicePortType">
    <operation name="GetBalance">
      <input message="tns:GetBalanceRequest"/>
      <output message="tns:GetBalanceResponse"/>
    </operation>
  </portType>
  
  <!-- 바인딩 및 엔드포인트 -->
  <binding name="BankServiceBinding" type="tns:BankServicePortType">
    <soap:binding transport="http://schemas.xmlsoap.org/soap/http"/>
    <operation name="GetBalance">
      <soap:operation soapAction="http://bank.example.com/GetBalance"/>
    </operation>
  </binding>
  
  <service name="BankService">
    <port name="BankServicePort" binding="tns:BankServiceBinding">
      <soap:address location="http://bank.example.com/services/bank"/>
    </port>
  </service>
</definitions>
```
#### 클라이언트에서 WSDL을 획득한다.
`GET /bank.example.com/services/bank?wsdl`
#### 클라이언트에서 WSDL을 기준으로 요청을 구성한다.
