using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using JetBrains.Annotations;
using UnityEngine;

public class Box : MonoBehaviour
{
    public Solver solver;
    
    [CanBeNull] public Player player;

    private List<GameObject> GetMovableBoxes(Vector2 direction) => solver.doubleSizeMode
        ? GetDoubleWidthMovableBoxes(direction)
        : GetSingleWidthMovableBoxes(direction, distanceStep: 1);

    private List<GameObject> GetSingleWidthMovableBoxes(Vector2 direction, int distanceStep)
    {
        var hitObjects = Physics2D.RaycastAll(transform.position, direction);
        var movableBoxObjects = new List<GameObject> { gameObject };
        var previousObject = gameObject;

        foreach (var hit in hitObjects)
        {
            if (
                !hit.collider.gameObject.CompareTag("Box") ||
                Mathf.Approximately(hit.transform.position.x, previousObject.transform.position.x)
            ) continue;

            var isNextBoxValid = Mathf.Approximately(
                hit.transform.position.x,
                previousObject.transform.position.x + distanceStep * direction.x
            );
            
            if (!isNextBoxValid) break;

            previousObject = hit.collider.gameObject;
            movableBoxObjects.Add(hit.collider.gameObject);
        }

        return movableBoxObjects;
    }

    private List<GameObject> GetDoubleWidthMovableBoxes(Vector2 direction) => (direction.x, direction.y) switch
    {
        (-1f, 0) or (1f, 0) => GetHorizontalBoxes(direction),
        (0, 1f) or (0, -1f) => GetVerticalBoxes(direction),
        _ => throw new ArgumentOutOfRangeException(nameof(direction), direction, null)
    };

    private List<GameObject> GetHorizontalBoxes(Vector2 direction) =>
        GetSingleWidthMovableBoxes(direction, distanceStep: 2);
    
    private List<GameObject> GetVerticalBoxes(Vector2 direction) 
    {
        var visitedPoints = new HashSet<Vector2>();
        var boxObjects = new List<GameObject> { gameObject };
        var queue = new Queue<Vector2>(GetDoubleBoxEdgeDirections());
        
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
            if (!objectInFront.CompareTag("Box")) continue;

            var isAlreadyAdded = boxObjects.Any(boxObject =>
                boxObject.transform.position.Equals(objectInFront.transform.position));

            if (!isAlreadyAdded) boxObjects.Add(objectInFront);

            var box = objectInFront.GetComponent<Box>();
            var boxEdgePositions = box.GetDoubleBoxEdgeDirections();
            boxEdgePositions.ForEach(boxPosition =>
            {
                if (visitedPoints.Contains(boxPosition)) return;

                queue.Enqueue(boxPosition);
                visitedPoints.Add(boxPosition);
            });
        }

        return boxObjects;
    }
    
    public List<Vector2> GetDoubleBoxEdgeDirections() => new()
    {
        transform.position,
        (Vector2)transform.position + new Vector2(0.5f, 0),
        (Vector2)transform.position + new Vector2(-0.5f, 0),
    };
    
    private void OnCollisionEnter2D(Collision2D collision)
    {
        if (!collision.gameObject.CompareTag("Player") || player is null) return;
        var direction = player.lastPlayerMove;
        
        foreach (var box in GetMovableBoxes(direction))
        {
            box.transform.position = (Vector2)box.transform.position + direction;
        }
    }
}
