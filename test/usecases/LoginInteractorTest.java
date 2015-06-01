package usecases;

import authorizer.Authorizer;
import authorizer.RejectingAuthorizerStub;
import de.bechte.junit.runners.context.HierarchicalContextRunner;
import entities.UserStub;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(HierarchicalContextRunner.class)
public class LoginInteractorTest {
  private LoginInteractorImpl interactor;
  private AuthorizerSpy authorizerSpy;
  private UserGatewaySpy gatewaySpy;
  private LoginPresenterSpy presenterSpy;

  @Before
  public void setupInteractor() {
    interactor = new LoginInteractorImpl();
    presenterSpy = new LoginPresenterSpy();
    interactor.setPresenter(presenterSpy);
  }

  public class ValidLoginTests {
    @Before
    public void setupValidLogin() {
      authorizerSpy = new AcceptingAuthorizerSpy();
      gatewaySpy = new UserGatewaySpy();
      interactor.setAuthorizer(authorizerSpy);
      interactor.setUserGateway(gatewaySpy);
    }

    @Test
    public void normalLogin() throws Exception {
      LoginRequest request = new LoginRequest();
      request.username = "username";
      request.password = "password";

      interactor.login(request);

      assertThat(authorizerSpy.getUsername(), is("username"));
      assertThat(authorizerSpy.getPassword(), is("password"));

      assertThat(gatewaySpy.getRequestedId(), is(AcceptingAuthorizerSpy.STUB_ID));

      LoginResponse response = presenterSpy.getInvokedResponse();
      assertThat(response.name, is(UserStub.STUB_NAME));
      assertThat(response.lastLoginTime, is(UserStub.STUB_TIME));
      assertThat(response.loginCount, is(UserStub.STUB_LOGIN_COUNT));
    }
  }

  public class InvalidLoginTests {
    Authorizer authorizer;
    UserGateway userGateway;

    @Before
    public void setupInvalidLogin() {
      authorizer = new RejectingAuthorizerStub();
      userGateway = new UserGatewayStub();
      interactor.setAuthorizer(authorizer);
      interactor.setUserGateway(userGateway);
    }

    @Test
    public void whenLoginFails_loginFailureMessageIsPresented() throws Exception {
      LoginRequest request = new LoginRequest();
      request.username = "bad_username";
      request.password = "bad_password";

      interactor.login(request);

      LoginResponse invokedResponse = presenterSpy.getInvokedResponse();
      assertThat(invokedResponse.message, is(LoginInteractor.LOGIN_FAILURE_MESSAGE));
    }
  }

  public class RepeatedLoginFailureTests {
    private UserGatewayStub userGatewayStub;

    @Before
    public void setupLoginFailures() {
      authorizerSpy = new RejectingAuthorizerSpy();
      userGatewayStub = new UserGatewayStub();
      interactor.setAuthorizer(authorizerSpy);
      interactor.setUserGateway(userGatewayStub);
    }

    @Test
    public void threeStrikesAndYouAreOut() throws Exception {
      LoginRequest request = new LoginRequest();
      request.username = "username";
      request.password = "bad_password";

      interactor.login(request);
      assertThat(authorizerSpy.heldUsername(), is(nullValue()));

      interactor.login(request);
      assertThat(authorizerSpy.heldUsername(), is(nullValue()));

      interactor.login(request);
      assertThat(authorizerSpy.heldUsername(), is("username"));
    }
  }

  public class MockedVersionOfRepeatedLoginFailure {
    private UserGatewayStub userGatewayStub;
    private RepeatedLoginAuthorizerMock authorizerMock;

    @Before
    public void setupLoginFailures() {
      authorizerMock = new RepeatedLoginAuthorizerMock();
      userGatewayStub = new UserGatewayStub();
      interactor.setAuthorizer(authorizerMock);
      interactor.setUserGateway(userGatewayStub);
    }

    @Test
    public void threeStrikesAndYouAreOut() throws Exception {
      LoginRequest request = new LoginRequest();
      request.username = "username";
      request.password = "bad_password";

      interactor.login(request);
      interactor.login(request);
      interactor.login(request);
      assertThat(authorizerMock.verifyHeldOnThirdAttempt("username"), is(true));
    }
  }

  public class LoginTestsUsingFakeAuthorizer {
    private Authorizer authorizer;
    private UserGateway userGateway;

    @Before
    public void setupForFake() {
      authorizer = new FakeAuthorizer();
      userGateway = new UserGatewayStub();
      interactor.setAuthorizer(authorizer);
      interactor.setUserGateway(userGateway);
    }

    @Test
    public void normalLogin() throws Exception {
      LoginRequest request = new LoginRequest();
      request.username = "gooduser";
      request.password = "goodpassword";

      interactor.login(request);

      LoginResponse response = presenterSpy.getInvokedResponse();
      assertThat(response.name, is(UserStub.STUB_NAME));
      assertThat(response.lastLoginTime, is(UserStub.STUB_TIME));
      assertThat(response.loginCount, is(UserStub.STUB_LOGIN_COUNT));
    }

    @Test
    public void whenLoginFails_loginFailureMessageIsPresented() throws Exception {
      LoginRequest request = new LoginRequest();
      request.username = "bad_username";
      request.password = "bad_password";

      interactor.login(request);

      LoginResponse invokedResponse = presenterSpy.getInvokedResponse();
      assertThat(invokedResponse.message, is(LoginInteractor.LOGIN_FAILURE_MESSAGE));
    }

  }
}
