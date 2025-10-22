import React, { useEffect, useState } from "react";

export default function Profile({ userId, fetchWithAuth, onClose }) {
  const [user, setUser] = useState(null);
  const [groups, setGroups] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function fetchUserData() {
      try {
        // Fetch user info
        const userRes = await fetchWithAuth(`http://localhost:5000/api/users/${userId}`);
        const userData = await userRes.json();
        if (!userRes.ok) throw new Error(userData.error || "Failed to fetch user info");
        setUser(userData);

        // Fetch groups for this user
        const groupRes = await fetchWithAuth(`http://localhost:5000/api/groups/user/${userId}`);
        const groupData = await groupRes.json();
        if (!groupRes.ok) throw new Error(groupData.error || "Failed to fetch user groups");
        setGroups(groupData);
      } catch (err) {
        console.error(err);
        alert("Error fetching user info: " + err.message);
      } finally {
        setLoading(false);
      }
    }

    fetchUserData();
  }, [userId, fetchWithAuth]);

  if (loading) return <div>Loading profile...</div>;
  if (!user) return <div>Unable to load user info.</div>;

  return (
    <div className="card flex flex-col items-center p-4">
      <h2 className="text-xl font-semibold mb-4 text-center">Profile</h2>

      {/* User Info */}
      <div className="mb-6 text-center">
        <p><strong>Name:</strong> {user.name}</p>
        <p><strong>Birthday:</strong> {user.birthday || "N/A"}</p>
        <p><strong>Faculty:</strong> {user.faculty || "N/A"}</p>
      </div>

      {/* Groups List */}
      <div className="mb-6 w-full">
        <h3 className="text-lg font-semibold mb-2 text-center">Your Groups</h3>
        {groups.length > 0 ? (
          <ul className="profile-groups">
            {groups.map((group) => (
              <li key={group.id}>
                <strong>{group.name}</strong>
              </li>
            ))}
          </ul>
        ) : (
          <p className="text-center">You have not joined any groups yet.</p>
        )}
      </div>

      {/* Close Button */}
      <div className="profile-close-button">
        <button className="btn-cancel" onClick={onClose}>
          Close
        </button>
      </div>
    </div>
  );
}
