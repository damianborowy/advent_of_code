using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using UnityEngine;

public class Player : MonoBehaviour
{
    public float moveSpeed = 20f;
    public Vector2 lastPlayerMove;
    public Solver solver;

    private Vector2 _moveDirection;
    
    public IEnumerator MovePlayer(Vector2 direction)
    {
        if (!CanPlayerMove(direction)) yield break;
        
        var targetPosition = (Vector2)transform.position + direction;
        lastPlayerMove = direction;
        
        yield return MoveToPosition(targetPosition);
    }

    private bool GetCanPlayerMoveSingleWidth(Vector2 direction, int boxWidth)
    {
        var hitObjects = Physics2D.RaycastAll(transform.position, direction);

        var wall = hitObjects.First(obj => obj.collider.gameObject.CompareTag("Wall"));
        var wallDistance = Mathf.RoundToInt(wall.distance) - 1;

        var boxesBeforeWall = hitObjects
            .Where(obj => obj.collider.gameObject.CompareTag("Box") && obj.distance < wallDistance);

        return wallDistance > boxesBeforeWall.Count() * boxWidth;
    }

    private bool GetCanPlayerMoveDoubleWidth(Vector2 direction) => (direction.x, direction.y) switch
    {
        (-1f, 0) or (1f, 0) => GetCanPlayerMoveHorizontal(direction),
        (0, 1f) or (0, -1f) => GetCanPlayerMoveVertical(direction),
        _ => throw new ArgumentOutOfRangeException(nameof(direction), direction, null)
    };

    private bool GetCanPlayerMoveHorizontal(Vector2 direction) => GetCanPlayerMoveSingleWidth(direction, boxWidth: 2);

    private bool GetCanPlayerMoveVertical(Vector2 direction)
    {
        var visitedPoints = new HashSet<Vector2>();
        var queue = new Queue<Vector2>();
        queue.Enqueue(transform.position);
        
        while (queue.Count > 0)
        {
            var position = queue.Dequeue();
            var hits = Physics2D.RaycastAll(position, direction, 1);

            var firstHit = hits.FirstOrDefault(hit => 
                !hit.collider.gameObject.CompareTag("Player") &&
                !Mathf.Approximately(hit.transform.position.y, position.y)
            );
            
            var objectInFront = firstHit.collider?.gameObject;
            
            if (objectInFront is null) continue;
            if (objectInFront.CompareTag("Wall")) return false;
            if (!objectInFront.CompareTag("Box")) continue;
            
            var box = objectInFront.GetComponent<Box>();
            var boxEdgePositions = box.GetDoubleBoxEdgeDirections();
            boxEdgePositions.ForEach(boxPosition =>
            {
                if (visitedPoints.Contains(boxPosition)) return;
                
                queue.Enqueue(boxPosition);
                visitedPoints.Add(boxPosition);
            });
        }
        
        return true;
    }
    
    private bool CanPlayerMove(Vector2 direction) => solver.doubleSizeMode
        ? GetCanPlayerMoveDoubleWidth(direction)
        : GetCanPlayerMoveSingleWidth(direction, boxWidth: 1);
    
    private IEnumerator MoveToPosition(Vector2 targetPosition)
    {
        while ((Vector2)transform.position != targetPosition)
        {
            transform.position = Vector2.MoveTowards(transform.position, targetPosition, moveSpeed * Time.deltaTime);
            yield return null;
        }
    }
}
