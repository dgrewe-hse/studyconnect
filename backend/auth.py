from functools import wraps
from flask import request, jsonify, g
from keycloak import KeycloakOpenID, KeycloakAdmin, KeycloakError
import os
from datetime import datetime
from models import User, db   # adjust imports if your models are elsewhere


# ==========================================================
# Keycloak Clients
# ==========================================================

# 🔐 Public client (used by frontend for login, refresh, userinfo)
keycloak_openid = KeycloakOpenID(
    server_url=os.getenv("KEYCLOAK_SERVER_URL"),
    client_id=os.getenv("KEYCLOAK_CLIENT_ID"),
    realm_name=os.getenv("KEYCLOAK_REALM"),
    client_secret_key=os.getenv("KEYCLOAK_CLIENT_SECRET"),
)

# 🧩 Admin service account (client credentials flow)
keycloak_admin = KeycloakAdmin(
    server_url=os.getenv("KEYCLOAK_SERVER_URL"),
    realm_name=os.getenv("KEYCLOAK_REALM"),
    client_id=os.getenv("KEYCLOAK_ADMIN_CLIENT_ID"),
    client_secret_key=os.getenv("KEYCLOAK_ADMIN_CLIENT_SECRET"),
    verify=True,
)


# ==========================================================
# Decorator to Protect Routes
# ==========================================================
def keycloak_protect(f):
    """Decorator to protect endpoints with Keycloak token"""
    @wraps(f)
    def decorated(*args, **kwargs):
        auth_header = request.headers.get("Authorization", None)
        refresh_header = request.headers.get("X-Refresh-Token", None)

        if not auth_header or not auth_header.startswith("Bearer "):
            return jsonify({"error": "Missing or invalid Authorization header"}), 401

        access_token = auth_header.split(" ")[1]
        g.access_token = access_token

        try:
            userinfo = keycloak_openid.userinfo(access_token)
            g.user = userinfo
        except Exception as e:
            if refresh_header:
                try:
                    new_tokens = keycloak_openid.refresh_token(refresh_header)
                    g.access_token = new_tokens["access_token"]
                    g.user = keycloak_openid.userinfo(g.access_token)
                except Exception as e2:
                    return jsonify({"error": "Token invalid or expired", "details": str(e2)}), 401
            else:
                return jsonify({"error": "Token invalid or expired", "details": str(e)}), 401

        request.user = g.user
        return f(*args, **kwargs)
    return decorated
