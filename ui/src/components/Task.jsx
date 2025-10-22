import React, { useState, useEffect } from "react";

export default function EditTaskModal({ task, onSave, onCancel, userId, fetchWithAuth }){
  const [title, setTitle] = useState("");
  const [deadline, setDeadline] = useState("");
  const [kind, setKind] = useState("");
  const [priority, setPriority] = useState("");
  const [taskType, setTaskType] = useState("my");
  const [selectedGroup, setSelectedGroup] = useState("");
  const [assignee, setAssignee] = useState("");
  const [notes, setNotes] = useState("");
  const [progress, setProgress] = useState(0);
  const [groups, setGroups] = useState([]);

  // Fetch groups from API
  useEffect(() => {
    const fetchGroups = async () => {
      try {
        const res = await fetch(`http://localhost:5000/api/groups/user/${userId}`);
        const data = await res.json();
        setGroups(data);
      } catch (err) {
        console.error("Failed to fetch groups:", err);
      }
    };

    fetchGroups();
  }, [userId]);

  // Populate task fields
  useEffect(() => {
    if (task) {
      setTitle(task.title || "");
      setDeadline(task.deadline || "");
      setKind(task.kind || "");
      setPriority(task.priority || "");
      setTaskType(task.taskType || "my");
      setSelectedGroup(task.group || "");
      setAssignee(task.assignee || "");
      setNotes(task.notes || "");
      setProgress(task.progress || 0);
    }
  }, [task]);
const handleSave = async () => {
  const updatedTask = {
    title,
    deadline,
    kind,
    priority,
    status: task.status,
    user_id: task.user_id,
    group_id: taskType === "group" ? selectedGroup : null,
    assignee,
    notes,
    progress,
  };

  try {
    const res = await fetchWithAuth(`http://localhost:5000/api/tasks/${task.id}`, {
      method: "PUT",
      body: JSON.stringify(updatedTask),
    });
    const data = await res.json();
    if (!res.ok) throw new Error(data.error || "Failed to update task");
    onSave(data.task);
  } catch (err) {
    alert("Error updating task: " + err.message);
  }
};


  const isValid =
    title.trim() &&
    deadline &&
    kind &&
    priority &&
    (taskType === "my" || (taskType === "group" && selectedGroup));

  if (!task) return null;

  return (
    <div className="modal-overlay">
      <div className="modal-content max-w-md w-full p-4">
        <h2 className="text-xl font-semibold mb-4">Edit Task</h2>
        <div className="space-y-3">
          {/* Mandatory Fields */}
          <div>
            <label className="block mb-1 font-medium">
              Title <span style={{ color: "red" }}>*</span>
            </label>
            <input
              type="text"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              className="w-full p-2 border rounded-md"
              placeholder="Title"
            />
          </div>

          <div>
            <label className="block mb-1 font-medium">
              Deadline <span style={{ color: "red" }}>*</span>
            </label>
            <input
              type="date"
              value={deadline}
              onChange={(e) => setDeadline(e.target.value)}
              className="w-full p-2 border rounded-md"
            />
          </div>

          <div>
            <label className="block mb-1 font-medium">
              Kind <span style={{ color: "red" }}>*</span>
            </label>
            <select
              value={kind}
              onChange={(e) => setKind(e.target.value)}
              className="w-full p-2 border rounded-md"
            >
              <option value="">Select kind</option>
              <option value="homework">Homework Assignment</option>
              <option value="exam">Exam Preparation</option>
              <option value="project">Project Milestone</option>
            </select>
          </div>

          <div>
            <label className="block mb-1 font-medium">
              Priority <span style={{ color: "red" }}>*</span>
            </label>
            <select
              value={priority}
              onChange={(e) => setPriority(e.target.value)}
              className="w-full p-2 border rounded-md"
            >
              <option value="">Select priority</option>
              <option value="low">Low</option>
              <option value="medium">Medium</option>
              <option value="high">High</option>
            </select>
          </div>

          {/* Toggle */}
          <div className={`toggle-switch ${taskType === "group" ? "group-active" : ""}`}>
            <div className="switch-slider"></div>
            <div
              className={`switch-option ${taskType === "my" ? "active" : ""}`}
              onClick={() => setTaskType("my")}
            >
              My Task
            </div>
            <div
              className={`switch-option ${taskType === "group" ? "active" : ""}`}
              onClick={() => setTaskType("group")}
            >
              Group Task
            </div>
          </div>

          {taskType === "group" && (
            <div>
              <label className="block mb-1 font-medium">
                Select Group <span style={{ color: "red" }}>*</span>
              </label>
              <select
                value={selectedGroup}
                onChange={(e) => setSelectedGroup(e.target.value)}
                className="w-full p-2 border rounded-md"
              >
                <option value="">-- Choose a Group --</option>
                {groups.map((group) => (
                  <option key={group.id} value={group.id}>
                    {group.name}
                  </option>
                ))}
              </select>
            </div>
          )}

          {/* Optional */}
          <input
            type="text"
            value={assignee}
            onChange={(e) => setAssignee(e.target.value)}
            placeholder="Assignee"
            className="w-full p-2 border rounded-md"
          />
          <textarea
            value={notes}
            onChange={(e) => setNotes(e.target.value)}
            placeholder="Notes"
            className="w-full p-2 border rounded-md"
          />

          {/* Progress */}
          <div className="mt-2">
            <label className="block mb-1">Progress: {progress}%</label>
            <input
              type="range"
              min="0"
              max="100"
              value={progress}
              onChange={(e) => setProgress(Number(e.target.value))}
              className="w-full"
            />
          </div>

          {/* Buttons */}
          <div className="flex gap-2 mt-4">
            <button className="btn-primary flex-1" onClick={handleSave} disabled={!isValid}>
              Save
            </button>
            <button className="btn-cancel flex-1" onClick={onCancel}>
              Cancel
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
