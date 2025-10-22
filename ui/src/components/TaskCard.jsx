import React from "react";
import { Draggable } from "react-beautiful-dnd";

export default React.memo(function TaskCard({ task, index, onClick }) {
  return (
    <Draggable draggableId={String(task.id)} index={index}>
      {(provided, snapshot) => {
        // Merge draggableProps.style last for proper transform
        const style = {
          ...provided.draggableProps.style,
          cursor: snapshot.isDragging ? "grabbing" : "grab",
        };

        return (
          <div
            ref={provided.innerRef}
            {...provided.draggableProps}
            {...provided.dragHandleProps}
            style={style}
            onClick={() => {
              if (!snapshot.isDragging) onClick(task);
            }}
          >
            <div
              className={`task-card ${snapshot.isDragging ? "dragging" : ""}`}
            >
              <div className="task-title">{task.title}</div>
              <div className="task-labels" style={{ marginTop: "4px" }}>
                <span className="label kind">{task.kind}</span>
                <span className={`label priority ${task.priority.toLowerCase()}`}>
                  {task.priority}
                </span>
              </div>
            </div>
          </div>
        );
      }}
    </Draggable>
  );
});
