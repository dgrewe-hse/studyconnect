import pytest
from flask import Flask
from backend.auth import keycloak_protect

# Tests the decorator when the Authorization header is completely missing.
def test_keycloak_protect_missing_header():
    app = Flask(__name__)

    @app.route("/secure")
    @keycloak_protect
    def secure():
        return "ok", 200

    client = app.test_client()
    response = client.get("/secure")  # no Header
    assert response.status_code == 401
    assert "Missing" in response.get_json()["error"]

# Tests the decorator when an invalid/unverified token is provided.
def test_keycloak_protect_invalid_header(monkeypatch):
    app = Flask(__name__)

    # Mock KeycloakOpenID.userinfo to simulate a token validation failure
    from backend import auth
    monkeypatch.setattr(auth.keycloak_openid, "userinfo", lambda token: (_ for _ in ()).throw(Exception("invalid")))

    @app.route("/secure")
    @auth.keycloak_protect
    def secure():
        return "ok", 200

    client = app.test_client()
    response = client.get("/secure", headers={"Authorization": "Bearer invalid"})
    assert response.status_code == 401
    assert "Token invalid" in response.get_json()["error"]
